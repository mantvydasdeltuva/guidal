package com.guidal.data.db.models

data class LocationModel(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val rating: Float,
    val address: String,
    val price: Float,
    val schedule: String,
    val latitude: Float,
    val longitude: Float,
)