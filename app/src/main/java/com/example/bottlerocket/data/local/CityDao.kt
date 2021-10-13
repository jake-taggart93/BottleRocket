package com.example.bottlerocket.data.local

import androidx.room.*

@Dao
interface CityDao {
    @Insert(entity = CityEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPage(cityEntity: CityEntity)

    @Query("SELECT * FROM city_table")
    suspend fun returnCityList(): List<CityEntity>

    @Delete(entity = CityEntity::class)
    suspend fun deleteCity(cityEntity: CityEntity)
}