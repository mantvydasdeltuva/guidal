package com.guidal.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// TODO KDoc
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {

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
}