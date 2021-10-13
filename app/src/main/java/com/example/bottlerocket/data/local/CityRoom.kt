package com.example.bottlerocket.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CityRoom: RoomDatabase() {

    abstract fun getDao(): CityDao

    companion object{
        private var INSTANCE: CityRoom? = null

        fun getInstance(context: Context): CityRoom = INSTANCE ?: synchronized(this){
            var temp = INSTANCE
            if (temp != null) return temp

            temp = Room.databaseBuilder(context,
            CityRoom::class.java,
            "city_database").build()

            INSTANCE = temp

            return temp
        }
    }
}