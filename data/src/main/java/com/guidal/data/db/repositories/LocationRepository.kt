package com.guidal.data.db.repositories

import com.guidal.data.db.daos.LocationDao
import com.guidal.data.db.models.LocationModel
import com.guidal.data.utils.DatabaseOperation
import com.guidal.data.utils.DatabaseOperationUtils
import javax.inject.Inject

interface LocationRepository {
    suspend fun getLocationsByCategory(categoryId: Int): DatabaseOperation<List<LocationModel>>
    suspend fun getLocationById(locationId: Int): DatabaseOperation<LocationModel>
    suspend fun getAllLocations(): DatabaseOperation<List<LocationModel>>
}

internal class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val databaseOperationUtils: DatabaseOperationUtils
) : LocationRepository {

    override suspend fun getLocationsByCategory(categoryId: Int): DatabaseOperation<List<LocationModel>> {
        return databaseOperationUtils.safeDatabaseOperation {
            locationDao.getLocationsByCategoryModel(categoryId)
        }
    }

    override suspend fun getLocationById(locationId: Int): DatabaseOperation<LocationModel> {
        return databaseOperationUtils.safeDatabaseOperation {
            locationDao.getLocationByIdModel(locationId)
        }
    }

    override suspend fun getAllLocations(): DatabaseOperation<List<LocationModel>> {
        return databaseOperationUtils.safeDatabaseOperation {
            locationDao.getAllLocationsModel()
        }
    }
}