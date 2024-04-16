package com.example.weather.vo

import jakarta.persistence.*

@Entity
data class Forecast(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0, // 초기값을 설정하여 생성자에서 필수로 입력받지 않도록 설정

    @Column(nullable = false)
    val baseDate: String,

    @Column(nullable = false)
    val baseTime: String,

    @Column(nullable = false)
    val nx: Int,

    @Column(nullable = false)
    val ny: Int,

    @Column(nullable = false)
    val dataType: String,

    @Column(nullable = false)
    val forecastValue: String,

    @Column(nullable = false)
    val forecastDate: String,

    @Column(nullable = false)
    val forecastTime: String
)
