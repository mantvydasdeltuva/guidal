package com.guidal.data.db.models

data class ForecastModel(
    val day: String,
    val temperature: Double,
    val type: String,
    val timestamp: Long
)