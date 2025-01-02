package com.guidal.data.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO KDoc
internal class Callback() : RoomDatabase.Callback() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        GlobalScope.launch {
            insertInitialData(db)
        }
    }

    private fun insertInitialData(db: SupportSQLiteDatabase) {
        try {
            // Initial gender values
            // Check if gender data already exists to prevent duplicate inserts
            val cursor0 = db.query("SELECT COUNT(*) FROM gender")
            cursor0.moveToFirst()
            val count0 = cursor0.getInt(0)
            cursor0.close()

            if (count0 == 0) {
                // Insert initial gender table values
                db.execSQL("""
                    INSERT INTO gender (id, name) 
                    VALUES 
                        (0, 'Other'),
                        (1, 'Male'),
                        (2, 'Female')
                """)
            }

            // Initial country values
            // Check if country data already exists to prevent duplicate inserts
            val cursor1 = db.query("SELECT COUNT(*) FROM country")
            cursor1.moveToFirst()
            val count1 = cursor1.getInt(0)
            cursor1.close()

            if (count1 == 0) {
                // Insert initial country table values
                db.execSQL("""
                    INSERT INTO country (id, name) 
                    VALUES 
                        (0, 'Other'),
                        (1, 'Lithuania'),
                        (2, 'Greece')
                """)
            }

            // Initial role values
            // Check if role data already exists to prevent duplicate inserts
            val cursor2 = db.query("SELECT COUNT(*) FROM role")
            cursor2.moveToFirst()
            val count2 = cursor2.getInt(0)
            cursor2.close()

            if (count2 == 0) {
                // Insert initial role table values
                db.execSQL("""
                    INSERT INTO role (id, name) 
                    VALUES 
                        (0, 'Guest'),
                        (1, 'User'),
                        (2, 'Administrator')
                """)
            }

            // Initial user values
            // Check if user data already exists to prevent duplicate insert
            val cursor3 = db.query("SELECT COUNT(*) FROM user WHERE email = 'guest@guidal.eu'")
            cursor3.moveToFirst()
            val count3 = cursor3.getInt(0)
            cursor3.close()

            if (count3 == 0) {
                // Insert initial guest user using SQL
                db.execSQL("""
                    INSERT INTO user (id, name, surname, email, gender_id, country_id, password, role_id) 
                    VALUES 
                        ('00000000-0000-0000-0000-000000000000', 'Guest', '', 'guest@guidal.eu', 0, 0, 'donotshare', 0),
                        ('99999999-9999-9999-9999-999999999999', 'Guidal', 'Patras', 'admin@guidal.eu', 1, 2, 'seriouslydonotshare', 2)
                """)
            }

            // Initial category_type values
            // Check if category_type data already exists to prevent duplicate insert
            val cursor4 = db.query("SELECT COUNT(*) FROM category_type")
            cursor4.moveToFirst()
            val count4 = cursor4.getInt(0)
            cursor4.close()

            if (count4 == 0) {
                // Insert initial category_type table values
                db.execSQL("""
                    INSERT INTO category_type (id, name) 
                    VALUES 
                        (1, 'Post'),
                        (2, 'Location')
                """)
            }

            // Initial category values
            // Check if category data already exists to prevent duplicate insert
            val cursor5 = db.query("SELECT COUNT(*) FROM category")
            cursor5.moveToFirst()
            val count5 = cursor5.getInt(0)
            cursor5.close()

            if (count5 == 0) {
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
        } catch (e: Exception) {
            Log.e("Callback", "Error inserting initial data", e)
        }
    }
}