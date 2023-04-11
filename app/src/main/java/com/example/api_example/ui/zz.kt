//package com.example.clothingsuggester
//​
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.clothingsuggester.data.WeatherData
//import com.example.clothingsuggester.databinding.HomeFragmentBinding
//import com.example.api_example.util.PrefUtil
//import com.google.gson.Gson
//import okhttp3.*
//import java.io.IOException
//import java.util.*
//​
//class HomeFragment: Fragment() {
//    lateinit var binding: HomeFragmentBinding
//    ​
//    private val client=OkHttpClient()
//    private lateinit var summerClothes:MutableList<MutableList<ClotheImage>>
//    private lateinit var winterClothes:MutableList<MutableList<ClotheImage>>
//    private lateinit var randomListSummer:MutableList<ClotheImage>
//    private lateinit var randomListWinter:MutableList<ClotheImage>
//    ​
//    ​
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding= HomeFragmentBinding.inflate(inflater,container,false)
//        //val temperature=9.0
//        // binding.text.text= temperature.toString()
//        ​
//        //setup(temperature)
//        ​
//        makeOKHTTPRequest()
//        ​
//        ​
//        return binding.root
//    }
//    ​
//    ​
//    private fun makeOKHTTPRequest() {
//        val url =HttpUrl.Builder()
//            .scheme("https")
//            .host("api.tomorrow.io")
//            .addPathSegment("v4")
//            .addPathSegment("timelines")
//            .addQueryParameter("location", "26.549999,31.700001")
//            .addQueryParameter("fields", "temperature")
//            .addQueryParameter("timesteps", "current")
//            .addQueryParameter("units", "metric")
//            .addQueryParameter("apikey", "cTAFl4IspRniZXVeIWCVvCheAI3FrhCA")
//            .build()
//        ​
//        val request=Request.Builder().url(url)
//            .build()
//        ​
//        client.newCall(request).enqueue(object :Callback{
//            override fun onFailure(call: Call, e: IOException){
//                throw Exception("Failed to make HTTP request")
//                ​
//            }
//            ​
//            override fun onResponse(call: Call, response: Response) {
//                response.body?.string()?.let {jsonString->
//                    val weatherData = Gson().fromJson(jsonString, WeatherData::class.java)
//                    requireActivity().runOnUiThread {
//                        val temperature = weatherData.data.timelines[0].intervals[0].values.temperature
//                        binding.text.text= temperature.toString()
//                        ​
//                        setup(temperature)
//                        ​
//                    }
//                    ​
//                }
//            }
//            ​
//        })
//        ​
//    }
//    ​
//    ​
//    ​
//    ​
//    fun setup(temperature:Double){
//        PrefUtil.initPrefUtil(requireContext())
//        divideClothesForGroups()
//        ​
//        val listOfClothe=suggestClothesBasedOnTemperature(temperature)
//        ​
//        saveClothes(temperature,listOfClothe)
//        ​
//        val adapter=ClotheAdapter(listOfClothe)
//        binding.recyclerClothe.adapter=adapter
//        ​
//        ​
//    }
//    private fun divideClothesForGroups(){
//        val listSummer1=mutableListOf(ClotheImage(R.drawable.summer_tshirt1)
//            , ClotheImage(R.drawable.summer_tshirt2)
//            ,ClotheImage(R.drawable.pants4)
//            ,ClotheImage(R.drawable.pants6)
//            ,ClotheImage(R.drawable.summer_tshirt5)
//            , ClotheImage(R.drawable.summer_tshirt6)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        val listSummer2=mutableListOf(ClotheImage(R.drawable.summer_tshirt3)
//            , ClotheImage(R.drawable.summer_tshirt4)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//            ,ClotheImage(R.drawable.summer_tshirt5)
//            , ClotheImage(R.drawable.summer_tshirt6)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        val listSummer3=mutableListOf(ClotheImage(R.drawable.tshirtf1)
//            , ClotheImage(R.drawable.tshirtf2)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//            ,ClotheImage(R.drawable.summer_tshirt5)
//            , ClotheImage(R.drawable.summer_tshirt6)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        val listWinter1=mutableListOf(ClotheImage(R.drawable.winter_clothe1)
//            , ClotheImage(R.drawable.winter_clothe3)
//            ,ClotheImage(R.drawable.pants4)
//            ,ClotheImage(R.drawable.pants6)
//            ,ClotheImage(R.drawable.winter_jacket)
//            , ClotheImage(R.drawable.winter_jacket)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        val listWinter2=mutableListOf(ClotheImage(R.drawable.winter_clothe4)
//            , ClotheImage(R.drawable.winter_clothe2)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//            ,ClotheImage(R.drawable.winter_jacket)
//            , ClotheImage(R.drawable.winter_jacket)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        val listWinter3=mutableListOf(ClotheImage(R.drawable.winterf2)
//            , ClotheImage(R.drawable.whinterf1)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//            ,ClotheImage(R.drawable.winter_jacket)
//            , ClotheImage(R.drawable.winter_jacket)
//            ,ClotheImage(R.drawable.pants1)
//            ,ClotheImage(R.drawable.pants5)
//        )
//        ​
//        summerClothes= mutableListOf(listSummer1,listSummer2,listSummer3)
//        winterClothes= mutableListOf(listWinter1,listWinter2,listWinter3)
//        val random= Random()
//        val randomIndex=random.nextInt(summerClothes.size)
//        randomListSummer=summerClothes[randomIndex]
//        randomListWinter=winterClothes[randomIndex]
//        ​
//        ​
//    }
//    private fun suggestClothesBasedOnTemperature(temperature:Double):MutableList<ClotheImage>{
//        var  listOfClothe:MutableList<ClotheImage>
//        if(temperature<25)
//        {
//            ​
//            if(loadTemp()!=0F&&loadTemp()==temperature.toFloat())
//                listOfClothe= loadSaveClothes() as MutableList<ClotheImage>
//            else
//                listOfClothe=randomListWinter
//            ​
//            if( loadTemp()!=0F &&listOfClothe==loadSaveClothes()&&loadTemp()!=temperature.toFloat())
//            {
//                val newWinterClothes=winterClothes.filter { it!=loadSaveClothes() }
//                val randomIndex=Random().nextInt(newWinterClothes.size)
//                listOfClothe=newWinterClothes[randomIndex]
//                ​
//            }
//            ​
//        }
//        else{
//            if(loadTemp()!=0F&&loadTemp()==temperature.toFloat())
//                listOfClothe= loadSaveClothes() as MutableList<ClotheImage>
//            else
//                listOfClothe=randomListSummer
//            ​
//            if(loadTemp()!=0F && listOfClothe==loadSaveClothes() && loadTemp()!=temperature.toFloat())
//            {
//                val newSummerClothes=summerClothes.filter { it!=loadSaveClothes() }
//                val randomIndex=Random().nextInt(newSummerClothes.size)
//                listOfClothe=newSummerClothes[randomIndex]
//                ​
//            }
//            ​
//        }
//        return listOfClothe
//        ​
//    }
//    ​
//    private fun saveClothes(temperature:Double,listOfClothe:MutableList<ClotheImage>){
//        PrefUtil.clotheList=listOfClothe
//        PrefUtil.temperature=temperature.toFloat()
//    }
//    ​
//    private fun loadSaveClothes():List<ClotheImage>{
//        return PrefUtil.clotheList
//    }
//    private fun loadTemp():Float{
//        return PrefUtil.temperature!!.toFloat()
//    }
//    ​
//    ​
//    ​
//    ​
//    ​
//    ​
//    ​
//}