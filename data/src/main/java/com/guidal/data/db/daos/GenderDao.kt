package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guidal.data.db.entities.GenderEntity

// TODO KDoc
// TODO Unit test
@Dao
internal interface GenderDao {
    @Insert
    suspend fun insert(gender: GenderEntity)

    @Query("SELECT * FROM gender")
    suspend fun getGenders(): List<GenderEntity>

    @Query("SELECT * FROM gender WHERE id = :id LIMIT 1")
    suspend fun getGenderById(id: Int): GenderEntity?

    @Query("SELECT * FROM gender WHERE name = :name LIMIT 1")
    suspend fun getGenderByName(name: String): GenderEntity?
}