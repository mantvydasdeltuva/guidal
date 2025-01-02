package com.guidal.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// TODO KDoc
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Insert initial category_type table values
        db.execSQL("""
            INSERT INTO category_type (id, name) 
            VALUES 
                (1, 'Post'),
                (2, 'Location')
        """)

        // Insert initial category table values
        db.execSQL("""
            INSERT INTO category (name, type_id) 
            VALUES 
                ('Transportation', 1),
                ('Shops', 1),
                ('Trails', 1),
                ('Must Visit', 2),
                ('Sightseeing', 2),
                ('Restaurants', 2),
                ('Beaches', 2),
                ('Night Life', 2),
                ('Favorites', 2)
        """)
    }
}