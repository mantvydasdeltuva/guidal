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

            // Initial location values
            // Check if location data already exists to prevent duplicate insert
            val cursor6 = db.query("SELECT COUNT(*) FROM location")
            cursor6.moveToFirst()
            val count6 = cursor6.getInt(0)
            cursor6.close()

            if (count6 == 0) {
                // Insert initial location table values
                db.execSQL("""
                    INSERT INTO location (category_id, title, description, rating, address, price, schedule, latitude, longitude) 
                    VALUES 
                        (4, 'Holy Church of Saint Andrew', 'The Holy Church of Saint Andrew (Agios Andreas) in Patras is one of the largest churches in Greece and is dedicated to Saint Andrew, the patron saint of Patras.', '4.9', 'Agiou Andreou 201, Patra 262 22', '0', 'Closed,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 18:00', '38.2424581', '21.7278516'),
                        (4, 'Archaeological Museum of Patras', 'This museum showcases artifacts from the Mycenaean to the Roman periods, including a vast collection of mosaics.', '4.8', 'New National Road Patras-Athens 38-40, Patras 264 42', '5', '08:00 - 20:00', '38.2444', '21.7350'),
                        (4, 'Castle of Patras', 'A Byzantine-era castle offering panoramic views of the city and the Gulf of Patras.', '4.7', 'Patras 262 25', '5', '08:00 - 20:00', '38.2466', '21.7358'),
                        (4, 'Roman Odeon of Patras', 'An ancient theater from the 1st century AD, still used today for cultural events.', '4.6', 'Agiou Georgiou Square, Patras 262 25', '5', '08:00 - 20:00', '38.2461', '21.7347'),
                        (4, 'Achaia Clauss Winery', 'Established in 1861, it is the oldest winery in Greece, known for its Mavrodaphne wine.', '4.7', 'Petroto, Patras 265 00', '6', '09:00 - 17:00', '38.2250', '21.7583'),
                        (4, 'Apollon Theatre', 'A 19th-century neoclassical theater designed by Ernst Ziller, hosting various performances.', '4.5', 'Georgiou I Square, Patras 262 21', '4', '10:00 - 22:00', '38.2469', '21.7345'),
                        (5, 'Saint Nicholas Staircase', 'A picturesque staircase connecting the upper and lower parts of the city, offering scenic views.', '4.4', 'Agios Nikolaos, Patras 262 22', '0', 'Open 24 hours', '38.2472', '21.7352'),
                        (5, 'Psila Alonia Square', 'A central square with cafes and a park, popular for leisurely strolls.', '4.3', 'Psila Alonia, Patras 262 25', '0', 'Open 24 hours', '38.2480', '21.7355'),
                        (8, 'Veso Mare Entertainment Complex', 'A modern complex featuring cinemas, restaurants, and shops.', '4.2', 'Akti Dimeon 17, Patras 262 22', '10', '10:00 - 02:00', '38.2455', '21.7351'),
                        (5, 'Patras Lighthouse', 'A reconstructed lighthouse offering views of the Gulf and a nearby cafe.', '4.5', 'Akti Dimeon, Patras 262 22', '0', 'Open 24 hours', '38.2458', '21.7353'),
                        (5, 'Municipal Gallery of Patras', 'An art gallery showcasing works by local and national artists.', '4.4', 'Mavrokordatou 6, Patras 262 23', '3', '09:00 - 14:00', '38.2463', '21.7349'),
                        (5, 'Patras Carnival Museum', 'A museum dedicated to the history of the famous Patras Carnival.', '4.6', 'Evmilou 8, Patras 262 22', '5', '09:00 - 14:00', '38.2467', '21.7350'),
                        (4, 'Rio-Antirrio Bridge', 'A cable-stayed bridge connecting the Peloponnese to mainland Greece.', '4.9', 'Rio, Patras 265 04', '0', 'Open 24 hours', '38.3217', '21.7886'),
                        (5, 'Kastro (Castle) of Rio', 'A fortress built by the Ottomans in the 15th century, located near the Rio-Antirrio Bridge.', '4.5', 'Rio, Patras 265 04', '0', '08:00 - 20:00', '38.3219', '21.7888'),
                        (5, 'Mycenaean Cemetery of Voudeni', 'An archaeological site with tombs dating back to the Mycenaean period.', '4.4', 'Voudeni, Patras 265 00', '0', '08:00 - 15:00', '38.2300', '21.7500'),
                        (5, 'Patras Science Park', 'A hub for research and innovation, offering tours and exhibitions.', '4.3', 'Stadiou, Platani, Patras 265 04', '0', '09:00 - 17:00', '38.2820', '21.7850'),
                        (5, 'Dasyllio Park', 'A pine-covered hill offering walking paths and views of the city.', '4.5', 'Dasyllio, Patras 262 25', '0', 'Open 24 hours', '38.2500', '21.7355'),
                        (5, 'Patras Municipal Library', 'A historic library with a vast collection of books and manuscripts.', '4.4', 'Mavrokordatou 15, Patras 262 23', '0', '08:00 - 20:00', '38.2465', '21.7348'),
                        (5, 'Roman Aqueduct of Patras', 'Remnants of an ancient aqueduct that supplied water to the city.', '4.3', 'Ano Poli, Patras 262 25', '0', 'Open 24 hours', '38.2475', '21.7356'),
                        (5, 'Patras Industrial Area', 'A zone showcasing the city’s industrial heritage.', '4.2', 'VIPE, Patras 265 00', '0', '08:00 - 18:00', '38.2305', '21.7505'),
                        (5, 'Trion Navarchon Square', 'A bustling square named after three admirals, surrounded by cafes and shops.', '4.4', 'Trion Navarchon, Patras 262 22', '0', 'Open 24 hours', '38.2468', '21.7351'),
                        (4, 'Agios Dimitrios Church', 'A historic church known for its beautiful frescoes.', '4.6', 'Agios Dimitrios, Patras 262 22', '0', '08:00 - 20:00', '38.2470', '21.7352'),
                        (7, 'Patras Marina', 'A marina offering docking facilities and waterfront dining options.', '4.5', 'Akti Dimeon, Patras 262 22', '5', 'Open 24 hours', '38.2458', '21.7353'),
                        (6, 'Salumeria Ristorante', 'A popular dining spot in Patras offering traditional Italian cuisine and an excellent wine list.', '4.6', 'Not specified', '5', '10:00 - 23:00', '38.2400', '21.7400'),
                        (6, 'Argo', 'A recommended seafood restaurant in Patras with fresh catches and a great view of the sea.', '4.5', 'Not specified', '5', '10:00 - 22:00', '38.2420', '21.7380'),
                        (6, 'Rooster', 'A cozy bistro with Mediterranean flavors and a stylish atmosphere.', '4.7', 'Not specified', '10', '12:00 - 23:00', '38.2450', '21.7370'),
                        (6, 'Banana Moon', 'A relaxed beachside restaurant offering fresh seafood and Mediterranean dishes.', '4.5', 'Not specified', '5', '10:00 - 22:00', '38.2470', '21.7390'),
                        (7, 'Town Beach', 'A popular beach close to the city center, great for swimming and relaxing.', '4.3', 'Patras', '0', 'Open 24 hours', '38.2400', '21.7300'),
                        (7, 'Psathopyrgos Beach', 'A tranquil beach located a bit further from the city, perfect for a peaceful day by the sea.', '4.4', 'Psathopyrgos, Patras 265 00', '0', 'Open 24 hours', '38.2710', '21.6830'),
                        (7, 'Rodini Beach', 'A beautiful beach with clear waters, ideal for a swim and sunbathing.', '4.2', 'Rodini, Patras', '0', 'Open 24 hours', '38.2380', '21.7320'),
                        (7, 'Lambiri Beach', 'A secluded beach with a serene atmosphere, great for those looking for quietness.', '4.5', 'Lambiri, Patras 265 00', '0', 'Open 24 hours', '38.2730', '21.7220'),
                        (8, 'Disco Room', 'A popular nightclub in Patras featuring DJ performances and vibrant parties.', '4.3', 'Not specified', '10', '22:00 - 04:00', '38.2425', '21.7365'),
                        (8, 'Frida Bar n’ Stage', 'A bar and live music venue, perfect for enjoying cocktails and local performances.', '4.5', 'Not specified', '5', '19:00 - 02:00', '38.2450', '21.7385'),
                        (8, 'Yoko Bar', 'A trendy bar with a great selection of drinks and a lively atmosphere for nightlife enthusiasts.', '4.4', 'Not specified', '10', '21:00 - 03:00', '38.2460', '21.7390'),
                        (8, 'Café del Mar', 'An elegant bar offering a perfect blend of cocktails and an amazing sea view, popular for evening outings.', '4.6', 'Not specified', '5', '17:00 - 02:00', '38.2480', '21.7400')
                """)
            }
        } catch (e: Exception) {
            Log.e("Callback", "Error inserting initial data", e)
        }
    }
}