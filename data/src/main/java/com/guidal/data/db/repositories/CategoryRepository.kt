package com.guidal.data.db.repositories

import com.guidal.data.db.daos.CategoryDao
import com.guidal.data.db.models.CategoryModel
import com.guidal.data.utils.DatabaseOperation
import com.guidal.data.utils.DatabaseOperationUtils
import javax.inject.Inject

// TODO KDoc
interface CategoryRepository {
    suspend fun getCategories(): DatabaseOperation<List<CategoryModel>>
}

// TODO KDoc
// TODO Unit test
internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val databaseOperationUtils: DatabaseOperationUtils
) : CategoryRepository {

    override suspend fun getCategories(): DatabaseOperation<List<CategoryModel>> {
        return databaseOperationUtils.safeDatabaseOperation {
            categoryDao.getCategoriesModel()
        }
    }
}