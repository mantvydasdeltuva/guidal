package com.guidal.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.guidal.data.db.models.CategoryModel

@Dao
internal interface CategoryDao {
    @Transaction
    @Query("""
        SELECT 
            category.id AS id,
            category.name as name,
            category_type.name as type,
            category.description as description
        FROM category
        INNER JOIN category_type ON category.type_id = category_type.id
    """)
    suspend fun getCategoriesModel() : List<CategoryModel>
}