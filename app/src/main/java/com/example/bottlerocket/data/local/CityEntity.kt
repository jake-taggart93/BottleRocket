package com.example.bottlerocket.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    val geoCityID: Int
)
