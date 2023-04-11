package com.example.api_example.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.api_example.R
import com.example.api_example.data.WeatherInformation
import com.example.api_example.databinding.ActivityMainBinding
import com.example.api_example.util.PrefUtil
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()
    private lateinit var summerClothes: MutableList<MutableList<Clothes>>
    private lateinit var winterClothes: MutableList<MutableList<Clothes>>
    private lateinit var randomListSummer: MutableList<Clothes>
    private lateinit var randomListWinter: MutableList<Clothes>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        apiInformationResponse()
    }

    private fun apiInformationResponse() {
        dateAndTime()
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=cairo&appid=305a0a3806867ca5ad56e6fe87050bc9")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { it ->
                    val result = Gson().fromJson(it, WeatherInformation::class.java)
                    runOnUiThread {
                        binding.weatherCard.apply {
                            maxTemp.text =
                                kelvinToCelsius(result.main.temp_max).toInt().toString() + "°C"
                            feelsLikeText.text =
                                "Feels Like " + kelvinToCelsius(result.main.feels_like).toInt()
                                    .toString() + "°"
                            Description.text = result.weather.joinToString { it.description }
                            Windspeed.text = result.wind.speed.toInt().toString() + " km/h"
                            HumidityPercentage.text = result.main.humidity.toString() + "%"
                            pressureMB.text = result.main.pressure.toString() + " MB"
                            setup(result.main.temp_max - 273.15, presentDay())
                        }
                    }
                }
            }
        }
        )
    }

    fun setup(temperature: Double, date: String) {
        PrefUtil.initPrefUtil(this)
        pickStyle()
        val listOfClothes = suggestClothesBasedOnTemperature(temperature, date)
        saveClothes(date, listOfClothes)
        val adapter = clothesAdapter(listOfClothes)
        binding.aboutRecyclerView.adapter = adapter
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
        val formattedTime: String = currentTime.format(formatter)
        return formattedTime
    }

    private fun pickStyle() {

        val summerlist1 = mutableListOf(
            Clothes(R.drawable.shirt1),
            Clothes(R.drawable.shirt2),
            Clothes(R.drawable.shirt3),
            Clothes(R.drawable.short2),
            Clothes(R.drawable.short1),
            Clothes(R.drawable.short3),
            Clothes(R.drawable.sneaker1),
            Clothes(R.drawable.sneaker2),
            Clothes(R.drawable.sneaker3),
        )

        val summerlist2 = mutableListOf(
            Clothes(R.drawable.shirt3),
            Clothes(R.drawable.shirt2),
            Clothes(R.drawable.shirt1),
            Clothes(R.drawable.short1),
            Clothes(R.drawable.short1),
            Clothes(R.drawable.short3),
            Clothes(R.drawable.sneaker3),
            Clothes(R.drawable.sneaker2),
            Clothes(R.drawable.sneaker1)
        )


        val winterlist1 = mutableListOf(
            Clothes(R.drawable.shirt32),
            Clothes(R.drawable.shirt33),
            Clothes(R.drawable.shite31),
            Clothes(R.drawable.pants1),
            Clothes(R.drawable.pants2),
            Clothes(R.drawable.pants3),
            Clothes(R.drawable.sneaker6),
            Clothes(R.drawable.shoes5),
            Clothes(R.drawable.sneaker3),
        )


        val winterlist2 = mutableListOf(
            Clothes(R.drawable.shirt33),
            Clothes(R.drawable.shirt32),
            Clothes(R.drawable.shite31),
            Clothes(R.drawable.pants2),
            Clothes(R.drawable.pants1),
            Clothes(R.drawable.pants3),
            Clothes(R.drawable.sneaker3),
            Clothes(R.drawable.sneaker2),
            Clothes(R.drawable.sneaker6),
        )
        summerClothes = mutableListOf(summerlist1, summerlist2)
        winterClothes = mutableListOf(winterlist1, winterlist2)
        val random = Random()
        val randomIndex = random.nextInt(summerClothes.size)
        randomListSummer = summerClothes[randomIndex]
        randomListWinter = winterClothes[randomIndex]
    }

    private fun suggestClothesBasedOnTemperature(
        temperature: Double,
        date: String,
    ): MutableList<Clothes> {
        var listOfClothes: MutableList<Clothes>
        if (temperature < 15) {

            listOfClothes = if (loadTemp() != "" && loadTemp() == date)
                loadSaveClothes() as MutableList<Clothes>
            else
                randomListWinter

            if (loadTemp() != "" && listOfClothes == loadSaveClothes() && loadTemp() != date) {
                val newWinterClothesList = winterClothes.filter { it != loadSaveClothes() }
                val randomIndex = Random().nextInt(newWinterClothesList.size)
                listOfClothes = newWinterClothesList[randomIndex]
            }
        } else {
            listOfClothes = if (loadTemp() != "" && loadTemp() == date)
                loadSaveClothes() as MutableList<Clothes>
            else
                randomListSummer

            if (loadTemp() != "" && listOfClothes == loadSaveClothes() && loadTemp() != date) {
                val newSummerList = summerClothes.filter { it != loadSaveClothes() }
                val randomIndex = Random().nextInt(newSummerList.size)
                listOfClothes = newSummerList[randomIndex]
            }
        }
        return listOfClothes

    }

    private fun saveClothes(date: String, listOfClothes: MutableList<Clothes>) {
        PrefUtil.clotheList = listOfClothes
        PrefUtil.date = date
    }

    private fun loadSaveClothes(): List<Clothes> {
        return PrefUtil.clotheList
    }

    private fun loadTemp(): String {
        return PrefUtil.date!!
    }

}