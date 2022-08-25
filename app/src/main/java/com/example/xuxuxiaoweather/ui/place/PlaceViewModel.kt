package com.example.xuxuxiaoweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.xuxuxiaoweather.logic.Repository
import com.example.xuxuxiaoweather.logic.model.Place
import retrofit2.Response
import retrofit2.http.Query


class PlaceViewModel : ViewModel() {

    fun savePlace(place: Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    var savableList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData){query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
}
