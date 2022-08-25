package com.example.xuxuxiaoweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.xuxuxiaoweather.WeatherApplication
import com.example.xuxuxiaoweather.logic.model.Place
import com.example.xuxuxiaoweather.logic.model.Places
import com.example.xuxuxiaoweather.ui.weather.WeatherActivity
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place:Place){
        val placeslist = getSavedPlace()
        if (!placeslist.places.contains(place)){
            placeslist.places.add(place)
        }

        sharedPreferences().edit {
            putString("places",Gson().toJson(placeslist))
        }
    }


    fun getSavedPlace():Places{
        val placeJson = sharedPreferences().getString("places","")
        if(placeJson.isNullOrEmpty()){
            val emptylist = ArrayList<Place>()
            return Places(emptylist)
        }else{
            return Gson().fromJson(placeJson,Places::class.java)
        }

    }

    fun isPlaceSaved() = sharedPreferences().contains("places")

    private fun sharedPreferences() = WeatherApplication.context
        .getSharedPreferences("xuxuxiaoWeather",Context.MODE_PRIVATE)
}
