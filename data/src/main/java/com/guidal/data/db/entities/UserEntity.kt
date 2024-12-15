package com.guidal.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

// TODO KDoc
// TODO Unit test
@Entity(
    tableName = "user",
    foreignKeys = [
        ForeignKey(
            entity = GenderEntity::class,
            parentColumns = ["id"],
            childColumns = ["gender_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = RoleEntity::class,
            parentColumns = ["id"],
            childColumns = ["role_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["country_id"]),
        Index(value = ["gender_id"]),
        Index(value = ["role_id"])
    ]
)
internal data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "gender_id")
    val genderId: Int,
    @ColumnInfo(name = "country_id")
    val countryId: Int,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "role_id")
    val roleId: Int
) {
    companion object {
        val DEFAULT_UUID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
        const val DEFAULT_SURNAME: String = ""
    }
}