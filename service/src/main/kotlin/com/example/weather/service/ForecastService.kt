package com.example.weather.service
import com.example.weather.repository.ForecastRepository
import com.example.weather.vo.Forecast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class ForecastService(private val forecastRepository: ForecastRepository) {

    @Value("\${my.api.key}")
    private lateinit var apiKey: String

    @Transactional
    fun getAndSaveForecasts(): List<Forecast> {
        val baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=$apiKey&numOfRows=10&pageNo=1&base_date=$baseDate&base_time=0500&dataType=JSON&nx=55&ny=127"
        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject(url, String::class.java)
        println("Response: $response")
        return if (response != null) parseAndSaveForecast(response) else emptyList()
    }

    private fun parseAndSaveForecast(json: String): List<Forecast> {
        val jsonObject = JSONObject(json.trim())
        val forecastsArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item")

        return List(forecastsArray.length()) { i ->
            val item = forecastsArray.getJSONObject(i)
            Forecast(
                baseDate = item.getString("baseDate"),
                baseTime = item.getString("baseTime"),
                nx = item.getInt("nx"),
                ny = item.getInt("ny"),
                dataType = item.getString("category"),
                forecastValue = item.getString("fcstValue"),
                forecastDate = item.getString("fcstDate"),
                forecastTime = item.getString("fcstTime")
            ).also { forecast ->
                forecastRepository.save(forecast)
            }
        }
    }

    fun getAllForecasts(): List<Forecast> = forecastRepository.findAll()
}