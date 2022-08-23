package com.example.xuxuxiaoweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val TOKEN = "5tGTaYFu91cC7kJ3"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}