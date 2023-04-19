package com.example.api_example.util

import okhttp3.*
import java.io.IOException

object ApiRequest {
    private val client = OkHttpClient()
fun currentWeatherStateRequest(
    onFailure: (e: IOException) -> Unit,
    onResponse: (response: Response) -> Unit,
    ) {
    val request = Request.Builder()
        .url("https://api.openweathermap.org/data/2.5/weather?q=cairo&appid=305a0a3806867ca5ad56e6fe87050bc9")
        .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = onFailure(e)
            override fun onResponse(call: Call, response: Response) = onResponse(response)
        })
    }
}