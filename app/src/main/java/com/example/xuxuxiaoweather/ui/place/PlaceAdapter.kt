package com.example.xuxuxiaoweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.xuxuxiaoweather.R
import com.example.xuxuxiaoweather.logic.model.Place
import com.example.xuxuxiaoweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val fragment: PlaceFragment,private val placeList:List<Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    var placeslist  = placeList



        inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
            val placeName:TextView = view.findViewById(R.id.placeName)
            val placeAddress :TextView = view.findViewById(R.id.placeAddress)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val place = placeslist[position]
            val activity = fragment.activity
            if(activity is WeatherActivity){
                activity.drawerlayout.closeDrawers()
                activity.viewModel.locationlng = place.location.lng
                activity.viewModel.locationlat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else{
                val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                    putExtra("location_lng",place.location.lng)
                    putExtra("location_lat",place.location.lat)
                    putExtra("place_name",place.name)
                }
                fragment.viewModel.savePlace(place)
                fragment.startActivity(intent)
                activity?.finish()
            }



        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeslist[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int {
        return placeslist.size
    }
}