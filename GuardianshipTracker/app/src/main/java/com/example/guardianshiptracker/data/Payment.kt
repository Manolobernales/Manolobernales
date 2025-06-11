package com.example.guardianshiptracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val memberId: Int,
    val month: Int,
    val year: Int,
    val amount: Double
)
