package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.guidal.data.db.models.LocationModel

@Dao
internal interface LocationDao {
    @Transaction
    @Query("""
        SELECT 
            location.id AS id,
            category.name as category,
            location.title as title,
            location.description as description,
            location.rating as rating,
            location.address as address,
            location.price as price,
            location.schedule as schedule,
            location.latitude as latitude,
            location.longitude as longitude,
            location.image as image
        FROM location
        INNER JOIN category ON location.category_id = category.id
        WHERE location.category_id = :categoryId
    """)
    suspend fun getLocationsByCategoryModel(categoryId: Int) : List<LocationModel>
}