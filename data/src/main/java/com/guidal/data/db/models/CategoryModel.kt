package com.guidal.data.db.models

data class CategoryModel(
    val id: Int,
    val name: String,
    val type: String,
    val description: String? = null
)