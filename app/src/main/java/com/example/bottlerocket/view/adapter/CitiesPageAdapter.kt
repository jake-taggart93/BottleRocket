package com.example.bottlerocket.view.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bottlerocket.databinding.PageCityLayoutBinding
import com.example.bottlerocket.view.CityFragment


class CitiesPageAdapter(
    private var cityListGeoID: List<Int>,
    private val hostActivity: FragmentActivity
) : FragmentStateAdapter(hostActivity) {

    fun updateCityList(cityListGeoID: List<Int>){
        this.cityListGeoID  = cityListGeoID
    }

    override fun getItemCount(): Int {
        return cityListGeoID.size
    }

    override fun createFragment(position: Int): Fragment {
        return CityFragment.newInstance(
            cityListGeoID[position]
        )
    }
}