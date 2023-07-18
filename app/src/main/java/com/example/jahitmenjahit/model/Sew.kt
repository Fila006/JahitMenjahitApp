package com.example.jahitmenjahit.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "sew_table")
data class Sew(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val manyorders: String,
    val size: String,
    val latitude: Double?,
    val longitude: Double?

    // menambahkan data latitude dan longitud untuk disimpan dalam tabel
    ) : Parcelable