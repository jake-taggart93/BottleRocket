package com.example.bottlerocket.repository

import com.example.bottlerocket.data.model.CityDetailResponse
import com.example.bottlerocket.data.model.CityResponse

sealed class PresentationData{
    data class ResponseCitySearch(val data : CityResponse): PresentationData()
    data class Loading(val isLoading: Boolean = true): PresentationData()
    data class ResponseCityDetails(val data : CityDetailResponse): PresentationData()
    data class Error(val errorMessage: String): PresentationData()
    object Empty: PresentationData()
    data class ResponseCityPageList(val data: List<Int>): PresentationData()
}
