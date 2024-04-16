package com.example.weather.controller

import com.example.weather.service.ForecastService
import com.example.weather.vo.Forecast
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/forecasts")
class ForecastController(private val forecastService: ForecastService) {

    @PostMapping("/forecast")
    fun createForecasts(): List<Forecast> = forecastService.getAndSaveForecasts()

    @GetMapping("/forecast")
    fun getForecasts(): ResponseEntity<List<Forecast>> {
        val forecasts = forecastService.getAllForecasts()
        return if (forecasts.isEmpty()) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(forecasts)
        }
    }
}