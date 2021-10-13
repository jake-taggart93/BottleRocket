package com.example.bottlerocket.data.model

data class CityResponse(
    val cities: List<City>
)

data class City(
    val geonameid: Int,
    val name: String,
    val longitude: Float,
    val latitude: Float,
    val imageURLs: ImageUrl,
    val timezone: String
)

data class ImageUrl(
    val androidImageURLs: ImageSize
)

data class ImageSize(
    val hdpiImageURL: String,
    val xhdpiImageURL: String,
    val mdpiImageURL: String
)

data class CityDetailResponse(
    val weather: CityWeather,
    val city: City
)

data class CityWeather(
    val days: List<Day>
)

data class Day(
    val hourlyWeather: List<HourlyWeather>,
    val low: Float,
    val dayOfTheWeek: Int,
    val weatherType: String
)

data class HourlyWeather(
    val temperature: Float,
    val windSpeed: Float,
    val rainChance: Float,
    val weatherType: String,
    val hour: Int,
    val humidity: Float
)