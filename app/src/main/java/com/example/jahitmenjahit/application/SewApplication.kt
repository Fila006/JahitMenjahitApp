package com.example.jahitmenjahit.application

import android.app.Application
import com.example.jahitmenjahit.repository.SewRepository

class SewApplication : Application() {
    val database by lazy { SewDatabase.getDatabase(this) }
    val repository by lazy { SewRepository(database.sewDao())}
}