package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.guidal.data.db.entities.ForecastEntity
import com.guidal.data.db.models.ForecastModel

@Dao
internal interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(forecasts: List<ForecastEntity>)

    @Query("SELECT * FROM forecast")
    suspend fun getForecastsModel() : List<ForecastModel>
}