package com.guidal.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO KDoc
// TODO Unit test
@Entity(
    tableName = "forecast",
)
internal data class ForecastEntity(
    @PrimaryKey
    @ColumnInfo(name = "day")
    val day: String,
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(
        name = "timestamp",
        defaultValue = "CURRENT_TIMESTAMP"
    )
    val timestamp: Long
)