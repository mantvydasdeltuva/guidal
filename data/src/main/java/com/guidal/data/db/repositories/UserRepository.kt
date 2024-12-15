package com.guidal.data.db.repositories

import com.guidal.data.db.daos.CountryDao
import com.guidal.data.db.daos.GenderDao
import com.guidal.data.db.daos.RoleDao
import com.guidal.data.db.daos.UserDao
import com.guidal.data.utils.DatabaseOperation
import com.guidal.data.db.models.UserModel
import com.guidal.data.utils.DatabaseOperationUtils
import javax.inject.Inject

// TODO KDoc
interface UserRepository {
    suspend fun registerUser(userModel: UserModel): DatabaseOperation<UserModel>
    suspend fun authenticateUser(email: String, password: String): DatabaseOperation<UserModel>
    suspend fun authenticateGuest(): DatabaseOperation<UserModel>
}

// TODO KDoc
// TODO Unit test
internal class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val genderDao: GenderDao,
    private val countryDao: CountryDao,
    private val roleDao: RoleDao,
    private val databaseOperationUtils: DatabaseOperationUtils
) : UserRepository {

    override suspend fun registerUser(userModel: UserModel): DatabaseOperation<UserModel> {
        return databaseOperationUtils.safeDatabaseOperation {
            val existingUser = userDao.getUserByEmail(userModel.email)
            if (existingUser != null) {
                throw IllegalArgumentException("Email is already taken.")
            }
            val userEntityFromModel = userModel.toUserEntity(genderDao, countryDao, roleDao)
            userDao.insert(userEntityFromModel)

            // Returning result
            userDao.getUserByEmailModel(userModel.email)
                ?: throw RuntimeException("Something went wrong. Try again.")
        }
    }

    override suspend fun authenticateUser(email: String, password: String): DatabaseOperation<UserModel> {
        return databaseOperationUtils.safeDatabaseOperation {
            val userModel = userDao.getUserByEmailModel(email)
                ?: throw IllegalArgumentException("Incorrect email.")
            if (password != userModel.password) {
                throw IllegalArgumentException("Incorrect password.")
            }

            // Returning result
            userModel
        }
    }

    override suspend fun authenticateGuest(): DatabaseOperation<UserModel> {
        return databaseOperationUtils.safeDatabaseOperation {
            // Returning result
            userDao.getUserByEmailModel("guest@guidal.eu")
                ?: throw IllegalArgumentException("This feature is currently not supported.")
        }
    }
}