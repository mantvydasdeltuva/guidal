package com.guidal.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import com.guidal.data.api.services.OpenWeatherMapService
import com.guidal.data.db.Callback
import com.guidal.data.db.daos.CountryDao
import com.guidal.data.db.daos.GenderDao
import com.guidal.data.db.daos.RoleDao
import com.guidal.data.db.daos.UserDao
import com.guidal.data.db.Database
import com.guidal.data.db.daos.CategoryDao
import com.guidal.data.db.daos.ForecastDao
import com.guidal.data.db.migrations.MigrationsRegistry
import com.guidal.data.db.repositories.CategoryRepository
import com.guidal.data.db.repositories.CategoryRepositoryImpl
import com.guidal.data.db.repositories.ForecastRepository
import com.guidal.data.db.repositories.ForecastRepositoryImpl
import com.guidal.data.db.repositories.UserRepository
import com.guidal.data.db.repositories.UserRepositoryImpl
import com.guidal.data.utils.DatabaseOperationUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// TODO KDoc
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    /*
     *  Database
     */

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        migrations:  Array<Migration>,
        callback: Callback
    ): Database {
        return Room.databaseBuilder(app, Database::class.java, "room")
//            .createFromAsset("database/room.db") // TODO Create pre-populated database and use migrations for data integrity changes, not for initial data
            .addMigrations(*migrations)
            .fallbackToDestructiveMigration() // TODO Do something about this :D
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideMigrations() : Array<Migration> {
        return MigrationsRegistry.migrations.toTypedArray()
    }

    @Provides
    @Singleton
    fun provideCallback() : Callback {
        return Callback()
    }

    /*
     *  User table
     */

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
        genderDao: GenderDao,
        countryDao: CountryDao,
        roleDao: RoleDao,
        databaseOperationUtils: DatabaseOperationUtils
    ): UserRepository {
        return UserRepositoryImpl(userDao, genderDao, countryDao, roleDao, databaseOperationUtils)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: Database): UserDao {
        return database.userDao()
    }

    /*
     *  Gender table
     */

    @Provides
    @Singleton
    fun provideGenderDao(database: Database): GenderDao {
        return database.genderDao()
    }

    /*
    *  Country table
    */

    @Provides
    @Singleton
    fun provideCountryDao(database: Database): CountryDao {
        return database.countryDao()
    }

    /*
     *  Role table
     */

    @Provides
    @Singleton
    fun provideRoleDao(database: Database): RoleDao {
        return database.roleDao()
    }

    /*
     *  Category table
     */

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao,
        databaseOperationUtils: DatabaseOperationUtils
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao, databaseOperationUtils)
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: Database): CategoryDao {
        return database.categoryDao()
    }

    /*
     *  Forecast table
     */

    @Provides
    @Singleton
    fun provideForecastRepository(
        forecastDao: ForecastDao,
        openWeatherMapService: OpenWeatherMapService,
        databaseOperationUtils: DatabaseOperationUtils,
        app: Application
    ): ForecastRepository {
        return ForecastRepositoryImpl(forecastDao, openWeatherMapService, databaseOperationUtils, app)
    }

    @Provides
    @Singleton
    fun provideForecastDao(database: Database): ForecastDao {
        return database.forecastDao()
    }
}