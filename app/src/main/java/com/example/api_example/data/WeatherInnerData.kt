package com.example.api_example.data

data class WeatherInnerData(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
    val temp_min: Double,
    val temp_max: Double,
    val feels_like:Double,
    val speed : Double,
    val pressure:Int,
    val humidity:Int,

    )