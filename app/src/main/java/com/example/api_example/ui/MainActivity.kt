package com.example.api_example.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api_example.util.ApiRequest
import com.example.api_example.data.WeatherInformation
import com.example.api_example.data.WeatherInnerData
import com.example.api_example.data.DataManger
import com.example.api_example.databinding.ActivityMainBinding
import com.example.api_example.util.PrefUtil
import com.google.gson.Gson
import okhttp3.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataManager = DataManger
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ApiRequest.currentWeatherStateRequest(::onFailure, ::onResponse)
    }

    private fun apiInformationResponse(WeatherInfo: WeatherInnerData) {
        dateAndTime()
        binding.weatherCard.apply {
            maxTemp.text =
                kelvinToCelsius(WeatherInfo.temp_max).toInt().toString().plus("°C")

            feelsLikeText.text = "Feels Like : ${
                kelvinToCelsius(WeatherInfo.feels_like).toInt().toString().plus("°")
            } "

            Description.text = WeatherInfo.description

            Windspeed.text = WeatherInfo.speed.toInt().toString().plus(" km/h")

            HumidityPercentage.text = WeatherInfo.humidity.toString().plus("%")

            pressureMB.text = WeatherInfo.pressure.toString().plus(" MB")
            setup(presentDay(), WeatherInfo.temp_max - 273.15)
        }
    }

    private fun setup(date: String, temperature: Double) {
        PrefUtil.initPrefUtil(this)
        val listOfClothes = suggestClothesBasedOnTemperature(temperature, date)
        saveClothes(date, listOfClothes)
        val adapter = ClothesAdapter(listOfClothes)
        binding.aboutRecyclerView.adapter = adapter
    }

    private fun suggestClothesBasedOnTemperature(temperature: Double, date: String): MutableList<Clothes> {
        val isColdWeather = temperature < 20
        val clothesList = if(loadDate() == "") {
            when {
                isColdWeather -> randomList(dataManager.winterClothes)
                else ->randomList(dataManager.summerClothes)
            }
        } else
            when {
                loadDate() == date -> loadSaveClothes() as MutableList<Clothes>
                isColdWeather -> generateRandomClothesList(dataManager.winterClothes, loadSaveClothes())
                else -> generateRandomClothesList(dataManager.summerClothes, loadSaveClothes())
            }
        return clothesList
    }
    private fun generateRandomClothesList(
        clothesSource: MutableList<MutableList<Clothes>>,
        savedClothes: List<Clothes>,
    ): MutableList<Clothes> {
        val randomListOfClothes= randomList(clothesSource)
        return if (savedClothes == randomListOfClothes) {
            val filteredClothes = clothesSource.filter { it != randomListOfClothes }
            val randomIndex = Random().nextInt(filteredClothes.size)
            filteredClothes[randomIndex]
        } else {
            randomListOfClothes
        }
    }

    private fun randomList(list:MutableList<MutableList<Clothes>>):MutableList<Clothes>{
        val random= Random()
        val randomIndex=random.nextInt(list.size)
        return list[randomIndex]
    }
    private fun saveClothes(date: String, listOfClothes: MutableList<Clothes>) {
        PrefUtil.clothesList = listOfClothes
        PrefUtil.date = date
    }

    private fun loadSaveClothes(): List<Clothes> {
        return PrefUtil.clothesList
    }

    private fun loadDate(): String {
        return PrefUtil.date!!
    }

    @SuppressLint("SetTextI18n")
    private fun dateAndTime() {
        binding.weatherCard.DayName.text = presentDay()
        binding.weatherCard.Time.text = time()
    }

    private fun presentDay(): String {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().time)
            .toString()
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    private fun time(): String {
        val currentTime: LocalTime = LocalTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return currentTime.format(formatter)
    }

    private fun onFailure(e: java.lang.Exception) {
        e.message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun onResponse(response: Response) {
        val weatherInfo = Gson().fromJson(response.body?.string(), WeatherInformation::class.java)
        runOnUiThread {
            apiInformationResponse(weatherInfo.main)

        }
    }
}