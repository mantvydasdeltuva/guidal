package com.guidal.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// TODO KDoc
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Insert initial gender table values
        db.execSQL("""
            INSERT INTO gender (id, name) 
            VALUES 
                (0, 'Other'),
                (1, 'Male'),
                (2, 'Female')
        """)

        // Insert initial country table values
        db.execSQL("""
            INSERT INTO country (id, name) 
            VALUES 
                (0, 'Other'),
                (1, 'Lithuania'),
                (2, 'Greece')
        """)

        // Insert initial role table values
        db.execSQL("""
            INSERT INTO role (id, name) 
            VALUES 
                (0, 'Guest'),
                (1, 'User'),
                (2, 'Administrator')
        """)

        // Insert initial user table values
        db.execSQL("""
            INSERT INTO user (id, name, surname, email, gender_id, country_id, password, role_id) 
            VALUES 
                ('00000000-0000-0000-0000-000000000000', 'Guest', '', 'guest@guidal.eu', 0, 0, 'donotshare', 0),
                ('99999999-9999-9999-9999-999999999999', 'Guidal', 'Patras', 'admin@guidal.eu', 1, 2, 'seriouslydonotshare', 2)
        """)
    }
}