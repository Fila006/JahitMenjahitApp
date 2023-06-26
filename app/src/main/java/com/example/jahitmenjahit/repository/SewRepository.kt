package com.example.jahitmenjahit.repository

import com.example.jahitmenjahit.dao.SewDao
import com.example.jahitmenjahit.model.Sew
import kotlinx.coroutines.flow.Flow

class SewRepository (private val sewDao: SewDao) {
    val allSew: Flow<List<Sew>> = sewDao.getAllSew()

    suspend fun insertSew(sew: Sew){
        sewDao.insertSew(sew)
    }
    suspend fun deleteSew(sew: Sew){
        sewDao.deleteSew(sew)
    }
    suspend fun updateSew(sew: Sew){
        sewDao.updateSew(sew)
    }
}