package com.example.bottlerocket.repository

import com.example.bottlerocket.data.local.CityDao
import com.example.bottlerocket.data.local.CityEntity
import com.example.bottlerocket.data.model.CityDetailResponse
import com.example.bottlerocket.data.model.CityResponse
import com.example.bottlerocket.data.remote.CityApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response


class Repository(private val dao: CityDao,
                 private val service: CityApi,
                 private val coroutineScope: CoroutineScope): IRepository {

    override fun collectCitySearch(cityName: String): Flow<PresentationData> {

        return flow{
            val response = service.getCityList(cityName)

            if (response.isSuccessful){
                // Success body
                emit(
                    createSuccessBodyCitySearch(response)
                )
            }else{
                // Error body
                emit(
                    createErrorBody(response.message())
                )
            }
        }
    }

    private fun createSuccessBodyCitySearch(response: Response<CityResponse>): PresentationData {
        return response.body()?.let {
            PresentationData.ResponseCitySearch(it)
        } ?: createErrorBody(response.message())
    }

    private fun createErrorBody(errorMessage: String): PresentationData {
        return PresentationData.Error(errorMessage)
    }

    override fun collectCityDetails(geoID: Int): Flow<PresentationData> {
        return flow {
            val response = service.getCityWeather(geoID)
            if (response.isSuccessful)
                emit(
                    createSuccessBodyCityDetails(response)
                )
            else
                emit(
                    createErrorBody(response.message())
                )
        }
    }

    private fun createSuccessBodyCityDetails(response: Response<CityDetailResponse>): PresentationData {
        return response.body()?.let {
            PresentationData.ResponseCityDetails(it)
        } ?: createErrorBody(response.message())
    }

    override fun storeCityGeoIDPage(geoID: Int) {
        coroutineScope.launch {
            dao.insertNewPage(createCityEntity(geoID))
        }
    }

    private fun createCityEntity(geoID: Int): CityEntity {
        return CityEntity(geoID)
    }

    override fun deleteCityGeoIDPage(geoID: Int) {
        coroutineScope.launch {
            dao.deleteCity(createCityEntity(geoID))
        }
    }

    override fun collectCityPageList(): Flow<PresentationData> {
        return flow {
            val response = dao.returnCityList()
            emit(
                PresentationData.ResponseCityPageList(
                    response.map { it.geoCityID }
                )
            )
        }
    }
}