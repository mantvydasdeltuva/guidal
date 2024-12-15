package com.guidal.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// TODO KDoc
// TODO Unit test
@Entity(tableName = "role")
internal data class RoleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val DEFAULT_ROLE_ID: Int = 1
    }
}