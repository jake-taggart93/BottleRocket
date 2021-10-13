package com.example.bottlerocket.repository

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun collectCitySearch(cityName: String): Flow<PresentationData>
    fun collectCityDetails(geoID: Int): Flow<PresentationData>
    fun collectCityPageList(): Flow<PresentationData>
    fun storeCityGeoIDPage(geoID: Int)
    fun deleteCityGeoIDPage(geoID: Int)
}