package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.guidal.data.db.entities.CountryEntity

// TODO KDoc
// TODO Unit test
@Dao
internal interface CountryDao {
    @Insert
    suspend fun insert(country: CountryEntity)

    @Query("SELECT * FROM country")
    suspend fun getCountries(): List<CountryEntity>

    @Query("SELECT * FROM country WHERE id = :id LIMIT 1")
    suspend fun getCountryById(id: Int): CountryEntity?

    @Query("SELECT * FROM country WHERE name = :name LIMIT 1")
    suspend fun getCountryByName(name: String): CountryEntity?
}