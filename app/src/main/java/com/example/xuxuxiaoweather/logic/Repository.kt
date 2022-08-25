package com.example.xuxuxiaoweather.logic

import androidx.lifecycle.liveData
import com.example.xuxuxiaoweather.logic.dao.PlaceDao
import com.example.xuxuxiaoweather.logic.model.Place
import com.example.xuxuxiaoweather.logic.model.Weather
import com.example.xuxuxiaoweather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query:String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherNetwork.searchPlace(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }

    }

    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async{
                WeatherNetwork.getRealtimeWeather(lng,lat)
            }
            var deferredDaily = async {
                WeatherNetwork.getDailyWeather(lng,lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(
                    RuntimeException(
                        "realtime response is ${realtimeResponse.status}"+
                                "daily response is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
    fun savePlace(place:Place)=PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}