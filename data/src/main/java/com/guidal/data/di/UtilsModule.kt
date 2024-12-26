package com.guidal.data.di

import android.app.Application
import com.guidal.data.utils.DataStoreUtils
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


    /*
     *  Data store
     */

    @Provides
    @Singleton
    internal fun provideDataStoreUtils(context: Application): DataStoreUtils {
        return DataStoreUtils(context)
    }
}