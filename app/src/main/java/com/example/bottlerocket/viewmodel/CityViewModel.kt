package com.example.bottlerocket.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bottlerocket.repository.IRepository
import com.example.bottlerocket.repository.PresentationData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CityViewModel(
    private val repository: IRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel("CityViewModel onCleared")
    }

    private val _cityList = MutableStateFlow<PresentationData>(PresentationData.Empty)
    val cityList: StateFlow<PresentationData> = _cityList

    private val _cityDetails = MutableStateFlow<PresentationData>(PresentationData.Empty)
    val cityDetails: StateFlow<PresentationData> = _cityDetails

    private val _cityPage = MutableStateFlow<PresentationData>(PresentationData.Empty)
    val cityPage: StateFlow<PresentationData> = _cityPage

    fun getCityList(cityName: String) {
        coroutineScope.launch {
            repository.collectCitySearch(cityName).collect {
                _cityList.value = PresentationData.Loading(false)
                delay(500)
                _cityList.value = it
            }
        }
    }

    fun getCityDetails(geoId: Int) {
        _cityDetails.value = PresentationData.Loading(true)
        coroutineScope.launch {
            repository.collectCityDetails(geoId).collect {
                _cityDetails.value = PresentationData.Loading(false)
                delay(500)
                _cityDetails.value = it
            }
        }
    }

    fun removeCityDetails(geoId: Int) {
        coroutineScope.launch {
            if (geoId != 4054852) {
                repository.deleteCityGeoIDPage(geoId)
                getAllCityPage()
            }
        }
    }

    fun insertCityPage(geoID: Int) {
        coroutineScope.launch {
            repository.storeCityGeoIDPage(geoID)
            getAllCityPage()
        }
    }

    fun getAllCityPage() {
        _cityPage.value = PresentationData.Loading(true)
        coroutineScope.launch {
            repository.collectCityPageList().collect {
                _cityPage.value = PresentationData.Loading(false)
                delay(500)
                _cityPage.value = it
            }
        }
    }
}