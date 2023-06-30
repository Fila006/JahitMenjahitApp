package com.example.jahitmenjahit.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jahitmenjahit.dao.SewDao
import com.example.jahitmenjahit.model.Sew


@Database(entities = [Sew::class], version = 1, exportSchema = false)
abstract class SewDatabase: RoomDatabase() {
    abstract fun sewDao(): SewDao

    companion object{
        private var INSTANCE: SewDatabase? = null

        fun getDatabase(context: Context): SewDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SewDatabase::class.java,
                    "sew_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}