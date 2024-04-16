package com.example.weather.service
import com.example.weather.repository.ForecastRepository
import com.example.weather.vo.Forecast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class ForecastService(private val forecastRepository: ForecastRepository) {

    @Value("\${my.api.key}")
    private lateinit var apiKey: String


    @Transactional
    fun getAndSaveForecasts(): List<Forecast> {
        val encodedServiceKey: String = URLEncoder.encode(apiKey, StandardCharsets.UTF_8.toString())
        val baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val url = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst")
            .queryParam("serviceKey", encodedServiceKey)
            .queryParam("numOfRows", "10")
            .queryParam("pageNo", "1")
            .queryParam("base_date", baseDate)
            .queryParam("base_time", "0500")
            .queryParam("dataType", "JSON")
            .queryParam("nx", "55")
            .queryParam("ny", "127")
            .build()
            .toUriString()

        val uri = URI(url)
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("parameters", headers)
        val response = restTemplate.exchange(uri, HttpMethod.GET, entity, String::class.java).body
        println("API Key: $apiKey")
        println("URL: $uri")
        println("Response: $response")

        return if (response != null && response.startsWith("{")) {
            parseAndSaveForecast(response)
        } else {
            emptyList()
        }
    }

    private fun parseAndSaveForecast(json: String): List<Forecast> {
        val jsonObject = JSONObject(json)
        val response = jsonObject.getJSONObject("response")
        val header = response.getJSONObject("header")
        val resultCode = header.getString("resultCode")

        if (resultCode == "00") {
            // API 응답 로깅
            println("Complete API Response: $json")

            // 'body' 객체의 존재 유무 확인
            if (response.has("body") && response.getJSONObject("body").has("items")) {
                val itemsObject = response.getJSONObject("body").getJSONObject("items")

                // 'item' 배열의 존재 유무 확인
                if (itemsObject.has("item")) {
                    val items = itemsObject.getJSONArray("item")
                    return List(items.length()) { i ->
                        val item = items.getJSONObject(i)
                        Forecast(
                            baseDate = item.getString("baseDate"),
                            baseTime = item.getString("baseTime"),
                            dataType = item.getString("category"),
                            forecastDate = item.getString("fcstDate"),
                            forecastTime = item.getString("fcstTime"),
                            forecastValue = item.getString("fcstValue"),
                            nx = item.getInt("nx"),
                            ny = item.getInt("ny")
                        ).also { forecast ->
                            forecastRepository.save(forecast)
                        }
                    }
                } else {
                    println("No 'item' array found in the response.")
                    return emptyList()
                }
            } else {
                println("No 'body' or 'items' object found in the response.")
                return emptyList()
            }
        } else {
            println("API Error: ${header.getString("resultMsg")}")
            return emptyList()
        }
    }

    fun getAllForecasts(): List<Forecast> = forecastRepository.findAll()
}