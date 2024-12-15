package com.guidal.data.di

import com.guidal.data.utils.DatabaseOperationUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// TODO KDoc
@Module
@InstallIn(SingletonComponent::class)
internal object UtilsModule {
    /*
     *  Database operation
     */

    @Provides
    @Singleton
    internal fun provideDatabaseOperationUtils(): DatabaseOperationUtils {
        return DatabaseOperationUtils()
    }
}