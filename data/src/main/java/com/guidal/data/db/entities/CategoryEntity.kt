package com.guidal.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// TODO KDoc
// TODO Unit test
@Entity(
    tableName = "category",
    foreignKeys = [
        ForeignKey(
            entity = CategoryTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["type_id"])
    ]
)
internal data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type_id")
    val typeId: Int,
    @ColumnInfo(name = "description")
    val description: String?
)