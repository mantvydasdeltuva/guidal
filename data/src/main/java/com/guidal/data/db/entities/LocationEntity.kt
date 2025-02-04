package com.guidal.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "location",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["category_id"])
    ]
)
internal data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "price", defaultValue = "0")
    val price: Float,
    @ColumnInfo(name = "schedule")
    val schedule: String,
    @ColumnInfo(name = "latitude")
    val lat: Float,
    @ColumnInfo(name = "longitude")
    val long: Float,
    @ColumnInfo(name = "image")
    val image: String?,
)