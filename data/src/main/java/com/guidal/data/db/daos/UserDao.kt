package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.guidal.data.db.entities.UserEntity
import com.guidal.data.db.models.UserModel
import java.util.UUID

// TODO KDoc
// TODO Unit test
@Dao
internal interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: UUID): UserEntity?

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Transaction
    @Query("""
        SELECT 
            user.id AS id,
            user.name AS name,
            user.surname as surname,
            user.email AS email,
            gender.name AS gender,
            country.name AS country,
            user.password AS password,
            role.name AS role
        FROM user
        INNER JOIN gender ON user.gender_id = gender.id
        INNER JOIN country ON user.country_id = country.id
        INNER JOIN role ON user.role_id = role.id
        WHERE user.id = :id
        LIMIT 1
    """)
    suspend fun getUserByIdModel(id: UUID): UserModel?

    @Transaction
    @Query("""
        SELECT 
            user.id AS id,
            user.name AS name,
            user.surname as surname,
            user.email AS email,
            gender.name AS gender,
            country.name AS country,
            user.password AS password,
            role.name AS role
        FROM user
        INNER JOIN gender ON user.gender_id = gender.id
        INNER JOIN country ON user.country_id = country.id
        INNER JOIN role ON user.role_id = role.id
        WHERE user.email = :email
        LIMIT 1
    """)
    suspend fun getUserByEmailModel(email: String): UserModel?
}