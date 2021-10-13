package com.example.bottlerocket.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottlerocket.R
import com.example.bottlerocket.data.model.City
import com.example.bottlerocket.data.model.CityDetailResponse
import com.example.bottlerocket.data.model.Day
import com.example.bottlerocket.databinding.PageCityLayoutBinding
import com.example.bottlerocket.repository.PresentationData
import com.example.bottlerocket.util.getDate
import com.example.bottlerocket.util.showError
import com.example.bottlerocket.util.showLoading
import com.example.bottlerocket.view.adapter.WeatherDailyAdapter
import com.example.bottlerocket.view.adapter.WeatherWeeklyAdapter
import com.example.bottlerocket.viewmodel.CityViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CityFragment: Fragment() {
    companion object{
        const val KEY_GEO_ID = "KEY_GEO_ID"
        fun newInstance(geoCityID: Int) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_GEO_ID, geoCityID)
                }
            }
    }

    private val viewModel: CityViewModel by inject()

    private val weeklyAdapter: WeatherWeeklyAdapter by lazy {
        WeatherWeeklyAdapter(::updateDailyWeather)
    }

    private val dailyAdapter: WeatherDailyAdapter by lazy {
        WeatherDailyAdapter()
    }

    private fun updateDailyWeather(dataItem: Day){
        dailyAdapter.submitList(dataItem.hourlyWeather)
        binding.cityDaily.adapter = dailyAdapter
    }

    private lateinit var binding: PageCityLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = PageCityLayoutBinding.inflate(inflater,
            container,
            false)
        initViews()
        initObservables()

        val geoID = arguments?.getInt(KEY_GEO_ID) ?: savedInstanceState?.getInt(KEY_GEO_ID) ?: 4054852

        viewModel.getCityDetails(geoID)

        return binding.root
    }

    private fun initObservables() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.cityDetails.collect {response->
                    when (response) {
                        is PresentationData.ResponseCityDetails -> updateDataSet(response.data)
                        is PresentationData.Loading -> updateLoading(response.isLoading)
                        is PresentationData.Error -> updateError(response.errorMessage)
                    }
                }
            }
        }
    }

    private fun updateError(errorMessage: String) {
        activity?.showError(errorMessage)
    }

    private fun updateLoading(loading: Boolean) {
        activity?.showLoading(loading)
    }

    private fun updateDataSetWeekly(data: List<Day>) {
        weeklyAdapter.submitList(data)
        binding.cityWeekly.adapter = weeklyAdapter
    }

    private fun updateDataSet(data: CityDetailResponse) {
        updateDataSetWeekly(data.weather.days)
        updateCityCard(data.city, data.weather.days[0].low)
    }

    private fun updateCityCard(city: City, low: Float) {
        binding.cityName.text = city.name
        binding.cityTemp.text = context?.getString(R.string.temp_indicator, low)
        binding.cityDate.text = city.timezone.getDate()
        Picasso.get().load(city.imageURLs.androidImageURLs.xhdpiImageURL)
            .into(binding.cityImage)
    }


    private fun initViews() {
        binding.cityDaily.layoutManager = LinearLayoutManager(context)
        binding.cityWeekly.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,
            false)
    }
}