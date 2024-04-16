package com.example.weather.repository

import com.example.weather.vo.Forecast
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ForecastRepository : JpaRepository<Forecast, Long>{

}