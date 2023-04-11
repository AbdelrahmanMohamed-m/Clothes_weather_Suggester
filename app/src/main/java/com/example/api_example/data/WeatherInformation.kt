package com.example.api_example.data

data class WeatherInformation(
    val weather: List<WeatherInnerData>,
    val main: WeatherInnerData,
    val wind : WeatherInnerData
)
