package com.guidal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.guidal.data.db.daos.CountryDao
import com.guidal.data.db.daos.GenderDao
import com.guidal.data.db.daos.RoleDao
import com.guidal.data.db.daos.UserDao
import com.guidal.data.db.entities.CountryEntity
import com.guidal.data.db.entities.GenderEntity
import com.guidal.data.db.entities.RoleEntity
import com.guidal.data.db.entities.UserEntity

// TODO KDoc
@Database(
    entities = [
        UserEntity::class,
        GenderEntity::class,
        CountryEntity::class,
        RoleEntity::class
    ],
    // Always increment version when database changes.
    // Update db.Callback for initial data (for fresh installs).
    // Update db.migrations.MigrationXToY and db.migrations.MigrationsRegistry for initial data and database integrity (for updates).
    version = 2,
    exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun genderDao() : GenderDao
    abstract fun countryDao() : CountryDao
    abstract fun roleDao() : RoleDao
}