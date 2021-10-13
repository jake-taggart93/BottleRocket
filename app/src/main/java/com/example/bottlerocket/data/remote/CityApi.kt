package com.example.bottlerocket.data.remote

import com.example.bottlerocket.BuildConfig
import com.example.bottlerocket.data.model.CityDetailResponse
import com.example.bottlerocket.data.model.CityResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CityApi {
    //https://weather.exam.bottlerocketservices.com/cities
    //https://weather.exam.bottlerocketservices.com/cities/4054852
    @GET(BuildConfig.END_POINT_CITIES)
    suspend fun getCityList(
        @Query("search")
        cityName: String): Response<CityResponse>

    @GET(BuildConfig.END_POINT_CITIES+"/{geoID}")
    suspend fun getCityWeather(
        @Path("geoID")
        geoID: Int): Response<CityDetailResponse>

    companion object{
        fun initRetrofit(): CityApi{
            return Retrofit.Builder()
                .client(getClient())
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CityApi::class.java)
        }

        private fun getClient(): OkHttpClient{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        }
    }
}