package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guidal.data.db.entities.RoleEntity

// TODO KDoc
// TODO Unit test
@Dao
internal interface RoleDao {
    @Insert
    suspend fun insert(role: RoleEntity)

    @Query("SELECT * FROM role WHERE id = :id LIMIT 1")
    suspend fun getRoleById(id: Int): RoleEntity?

    @Query("SELECT * FROM role WHERE name = :name LIMIT 1")
    suspend fun getRoleByName(name: String): RoleEntity?
}