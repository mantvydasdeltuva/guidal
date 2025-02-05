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
                    (4, 'Holy Church of Saint Andrew', 'The Holy Church of Saint Andrew (Agios Andreas) in Patras is one of the largest and most significant churches in Greece, dedicated to Saint Andrew, the patron saint of the city. Located near the site where Saint Andrew was martyred, the church serves as an important pilgrimage destination for Orthodox Christians. Its stunning Byzantine-style architecture features an impressive central dome, intricate mosaics, and beautiful frescoes. The church houses sacred relics of Saint Andrew, including part of the cross on which he was crucified. Thousands of visitors and worshippers gather here annually, especially on November 30th, Saint Andrew’s feast day.', '4.9', 'Agiou Andreou 201, Patra 262 22', '0', 'Closed,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 22:00,08:00 - 18:00', 38.2424581, 21.7278516),
                    (4, 'Archaeological Museum of Patras', 'This museum offers an impressive display of artifacts spanning from the Mycenaean to the Roman periods, providing a fascinating glimpse into ancient Greek history. Among its highlights is a vast collection of exquisite mosaics, showcasing intricate designs and scenes from daily life, mythology, and the gods. The museum also features a range of pottery, sculptures, jewelry, and tools, each item carefully preserved to represent the rich cultural heritage of ancient Greece. Visitors can explore the evolution of art, craftsmanship, and daily life through these captivating relics, offering a deep connection to the past.', '4.8', 'New National Road Patras-Athens 38-40, Patras 264 42', '5', '08:00 - 20:00', 38.244416, 21.734955),
                    (4, 'Castle of Patras', 'This Byzantine-era castle, perched on a hilltop, offers breathtaking panoramic views of the city of Patras and the expansive Gulf of Patras. Originally built for defense, the castle’s strong walls, towers, and strategic position make it a remarkable example of Byzantine military architecture. Visitors can walk along the ancient ramparts and explore the ruins, all while enjoying stunning vistas of the surrounding landscape, including the sea and the city below. The castle’s historical significance, combined with its commanding views, makes it a must-see destination for history buffs and those seeking a picturesque spot.', '4.7', 'Patras 262 25', '5', '08:00 - 20:00', 38.24481599618834, 21.74288983418859),
                    (4, 'Roman Odeon of Patras', 'This ancient theater, dating back to the 1st century AD, is one of the most well-preserved Roman structures in the region. Originally built for public performances and events, it features a remarkable seating arrangement and acoustic design that allow sound to travel perfectly across the space. Today, the theater continues to be an active cultural venue, hosting a variety of performances, including concerts, theatrical productions, and festivals. Its blend of historical significance and modern use makes it a unique location where visitors can experience the ancient past while enjoying contemporary events in an unforgettable setting.', '4.6', 'Agiou Georgiou Square, Patras 262 25', '5', '08:00 - 20:00', 38.243298876684456, 21.738287634756016),
                    (4, 'Achaia Clauss Winery', 'Established in 1861, Achaia Winery is the oldest winery in Greece and holds a significant place in the country’s winemaking history. It is particularly renowned for its Mavrodaphne wine, a rich and aromatic red wine made from the Mavrodaphne grape variety. The winery, with its long tradition, has played a key role in preserving and promoting Greek winemaking techniques. Visitors to Achaia Winery can explore its historic cellars, learn about the winemaking process, and taste a variety of wines, including the famous Mavrodaphne, while enjoying the scenic views of the surrounding vineyards.', '4.7', 'Petroto, Patras 265 00', '6', '09:00 - 17:00', 38.19690339039837, 21.76969215397892),
                    (4, 'Apollon Theatre', 'This 19th-century neoclassical theater, designed by the renowned architect Ernst Ziller, is a stunning example of classical architecture. With its elegant façade and grand interiors, the theater remains a cultural gem, hosting a wide range of performances, including plays, concerts, and dance productions. Ziller’s design emphasizes symmetry, refined proportions, and decorative details, capturing the spirit of the neoclassical era. Today, the theater continues to be a vibrant hub for the arts, attracting visitors and performers alike with its historic charm and dedication to preserving traditional cultural expressions. It stands as a testament to the cultural richness of the past and present.', '4.5', 'Georgiou I Square, Patras 262 21', '4', '10:00 - 22:00', 38.246552622038756, 21.735417586056766),
                    (5, 'Saint Nicholas Stairway', 'This picturesque staircase connects the upper and lower parts of the city, providing both a functional path and a stunning vantage point. As visitors ascend or descend the steps, they are treated to breathtaking views of the surrounding landscape, including the city’s rooftops, charming streets, and distant horizons. The staircase is often lined with vibrant plants and flowers, adding to its beauty and making the journey even more enjoyable. It serves as a popular spot for both locals and tourists, offering a peaceful escape while connecting the historic and modern parts of the city in a scenic way.', '4.4', 'Agios Nikolaos, Patras 262 22', '0', 'Open 24 hours', 38.24523137931459, 21.73954709474505),
                    (5, 'Psila Alonia Square', 'This central square, bustling with life, is a favorite spot for both locals and visitors looking to relax and unwind. Surrounded by charming cafes and shaded by trees, it offers a perfect setting for leisurely strolls or enjoying a quiet moment. The park within the square adds to its appeal, providing green space for picnics, outdoor reading, or simply soaking up the atmosphere. The square often serves as a gathering place for social events, making it a vibrant part of the community. Whether you’re sipping coffee or strolling through the park, it’s an ideal spot to experience the city’s laid-back charm.', '4.3', 'Psila Alonia, Patras 262 25', '0', 'Open 24 hours', 38.24112689540099, 21.735163943689045),
                    (8, 'Veso Mare Entertainment Complex', 'This modern complex offers a dynamic and vibrant experience, with a mix of cinemas, restaurants, and shops all in one location. The cinemas provide the latest films in a comfortable and contemporary setting, while the variety of restaurants cater to every taste, from casual bites to fine dining. Shoppers can enjoy a range of boutiques and stores, offering everything from fashion to electronics. The complex is designed for both convenience and entertainment, making it a popular destination for families, friends, and visitors looking to enjoy a day of entertainment, dining, and shopping all in a lively atmosphere.', '4.2', 'Akti Dimeon 17, Patras 262 22', '10', '10:00 - 02:00', 38.23772730011382, 21.726065261497276),
                    (5, 'Patras Lighthouse', 'This reconstructed lighthouse offers stunning views of the Gulf, making it a perfect spot for those seeking a scenic and peaceful experience. Standing tall on a rocky outcrop, the lighthouse provides panoramic vistas of the sparkling waters, nearby coastline, and distant horizon. Visitors can climb to the top to take in the breathtaking sights, while a charming cafe nearby allows for a relaxing break with a drink or snack. The combination of history, natural beauty, and modern amenities makes it a popular destination for tourists and locals alike, providing a perfect balance of exploration and leisure.', '4.5', 'Akti Dimeon, Patras 262 22', '0', 'Open 24 hours', 38.24509416522321, 21.725629321762792),
                    (5, 'Municipal Gallery of Patras', 'This art gallery showcases a diverse collection of works by both local and national artists, offering a unique insight into contemporary art. The space features a variety of mediums, including paintings, sculptures, photography, and mixed media, reflecting the creative expressions and cultural narratives of the artists. With rotating exhibitions, the gallery provides a dynamic platform for emerging talents and established artists alike. Visitors can immerse themselves in the rich artistic landscape, discover new perspectives, and engage in thought-provoking conversations about the artworks. It’s a vibrant hub for art lovers and a celebration of artistic innovation.', '4.4', 'Mavrokordatou 6, Patras 262 23', '3', '09:00 - 14:00', 38.24521803409347, 21.733025674250587),
                    (5, 'Patras Carnival Museum', 'This museum is dedicated to preserving and showcasing the rich history of the famous Patras Carnival, one of Greeces most vibrant and celebrated cultural events. The museum offers visitors a fascinating journey through the evolution of the carnival, from its early origins to the grand spectacles of today. It features a wide range of exhibits, including colorful costumes, masks, historical photos, and memorabilia from past parades and celebrations. The museum also highlights the cultural significance of the carnival, reflecting its role in local traditions and its influence on the wider Greek festival scene. It’s a must-visit for anyone interested in the lively spirit and history of this iconic event.', '4.6', 'Evmilou 8, Patras 262 22', '5', '09:00 - 14:00', 38.221677405788604, 21.721276007221707),
                    (4, 'Rio-Antirrio Bridge', 'This impressive cable-stayed bridge connects the Peloponnese peninsula to mainland Greece, spanning the Gulf of Corinth. Known for its striking modern design, the bridge is one of the longest and most technologically advanced in Europe. It features multiple towers and thick cables that provide both structural integrity and aesthetic appeal. The bridge not only serves as a vital transportation link, easing travel between the two regions, but also offers stunning views of the gulf and surrounding landscapes. An engineering marvel, the bridge is a symbol of Greeces progress and innovation, providing both practicality and beauty in its design.', '4.9', 'Rio, Patras 265 04', '0', 'Open 24 hours', 38.32048214330621, 21.77329908093552),
                    (5, 'Rio Fortress', 'This fortress, built by the Ottomans in the 15th century, stands as a historical landmark near the Rio-Antirrio Bridge, guarding the entrance to the Gulf of Corinth. Known as the Rio Fortress, it was constructed to control the strategic waterway and protect the region from naval invasions. The fortress features classic Ottoman military architecture, with sturdy walls, bastions, and defensive structures. Over the centuries, it has witnessed many historical events and remains a significant part of the area’s rich heritage. Visitors can explore its ruins while enjoying breathtaking views of the nearby bridge and the surrounding waters.', '4.5', 'Rio, Patras 265 04', '0', '08:00 - 20:00', 38.31111499795861, 21.78195923943054),
                    (5, 'Mycenaean Cemetery of Voudeni', 'This archaeological site is home to a series of tombs dating back to the Mycenaean period, offering a fascinating glimpse into ancient burial practices and the culture of this ancient civilization. The tombs, some of which are tholos (beehive-shaped) tombs, are meticulously constructed and contain valuable artifacts, including pottery, weapons, and jewelry, which provide insight into the lives and beliefs of the Mycenaeans. The site is an important archaeological find, revealing the complexity of the eras social and ritual customs. Visitors can explore these ancient burial sites and reflect on the rich history of one of Greeces earliest great civilizations.', '4.4', 'Voudeni, Patras 265 00', '0', '08:00 - 15:00', 38.251518998218216, 21.782353870149695),
                    (5, 'Patras Science Park', 'This hub for research and innovation is a dynamic space that fosters creativity, learning, and technological advancement. Offering a variety of tours and exhibitions, it showcases cutting-edge projects, breakthrough discoveries, and the latest developments in science, technology, and engineering. Visitors can explore interactive displays, attend workshops, and engage with experts to learn about groundbreaking research and its real-world applications. The hub is designed to inspire curiosity and spark new ideas, making it an ideal destination for students, professionals, and anyone interested in the future of innovation and knowledge. It serves as a catalyst for progress, connecting ideas and people.', '4.3', 'Stadiou, Platani, Patras 265 04', '0', '09:00 - 17:00', 38.29726476430259, 21.810960176414767),
                    (5, 'Dasyllio Park', 'This pine-covered hill offers a peaceful retreat from the bustle of the city, with well-maintained walking paths that wind through the fragrant pine trees. As visitors hike to the top, they are rewarded with stunning views of the city below, the surrounding landscape, and distant horizons. The hill provides a tranquil escape, perfect for nature walks, jogging, or simply relaxing in the shade of the pines. Its natural beauty and scenic vistas make it a popular spot for locals and tourists alike, offering a serene connection to nature while still being close to the heart of the city.', '4.5', 'Dasyllio, Patras 262 25', '0', 'Open 24 hours', 38.24877510979866, 21.745622967530064),
                    (5, 'Patras Municipal Library', 'This historic library is a treasure trove of knowledge, boasting a vast collection of books, manuscripts, and rare texts that span centuries. The library’s impressive architecture and atmosphere evoke a sense of timelessness, offering visitors a glimpse into the past while showcasing the evolution of literature, science, and culture. Its carefully curated collection includes ancient manuscripts, early printed books, and modern works, making it a valuable resource for scholars, researchers, and history enthusiasts. The library also hosts exhibitions, lectures, and events, further enriching its role as a center of learning and intellectual discovery. It’s a must-visit for anyone passionate about the written word.', '4.4', 'Mavrokordatou 15, Patras 262 23', '0', '08:00 - 20:00', 38.24503671473613, 21.732810436204787),
                    (5, 'Roman Aqueduct of Patras', 'The remnants of this ancient aqueduct offer a fascinating glimpse into the engineering marvels of the past. Built to supply water to the city, the aqueduct spans long distances, with stone arches and channels that once carried fresh water from distant sources to urban areas. The well-preserved sections of the aqueduct demonstrate the ingenuity and skill of ancient builders, showcasing their understanding of hydraulics and architecture. Visitors can explore these historic remains, walking along parts of the aqueduct and marveling at its construction, while gaining a deeper appreciation for the importance of water management in ancient civilizations.', '4.3', 'Ano Poli, Patras 262 25', '0', 'Open 24 hours', 38.24674984219903, 21.753039539427935),
                    (5, 'Patras Industrial Area', 'This zone dedicated to the city’s industrial heritage offers a captivating look at its historical roots in manufacturing and production. Through a collection of preserved factories, warehouses, and machinery, the area showcases the evolution of local industry, from early mechanization to the rise of modern technology. Visitors can explore interactive exhibits that highlight the role of key industries, such as textiles, shipbuilding, and mining, in shaping the citys development. The zone also features artifacts, photographs, and stories of the workers who contributed to this industrial legacy, providing a deeper understanding of how the city transformed through its industrial past.', '4.2', 'VIPE, Patras 265 00', '0', '08:00 - 18:00', 38.109566012140675, 21.639621381751155),
                    (5, 'Trion Navarchon Square', 'This bustling square, named after three admirals, is a vibrant hub of activity in the heart of the city. The square is surrounded by an array of charming cafes, boutiques, and shops, making it a popular spot for both locals and visitors to gather, relax, and shop. The admiration of the three admirals is honored through statues or plaques, adding historical significance to the lively atmosphere. Whether enjoying a coffee at a café or strolling through the nearby shops, the square offers a perfect blend of history, culture, and modern life, creating a dynamic space for all to enjoy.', '4.4', 'Trion Navarchon, Patras 262 22', '0', 'Open 24 hours', 38.24258177475184, 21.73006971875818),
                    (4, 'Agios Dimitrios Church', 'This historic church is renowned for its stunning frescoes, which adorn the walls and ceilings, creating a vibrant display of religious art. The frescoes, painted by skilled artisans, depict scenes from the Bible, saints, and important religious figures, capturing the essence of Byzantine art. The church’s architecture itself is also a sight to behold, with its intricate design and serene atmosphere. As visitors step inside, they are transported to a bygone era, where the combination of art, history, and spirituality creates an awe-inspiring environment. It’s a must-visit for art lovers, history enthusiasts, and those seeking a peaceful moment of reflection.', '4.6', 'Agios Dimitrios, Patras 262 22', '0', '08:00 - 20:00', 38.24041983850385, 21.741838873315256),
                    (7, 'Patras Marina', 'This marina offers top-notch docking facilities, making it an ideal spot for boat owners to dock their vessels while enjoying the vibrant waterfront atmosphere. The marina is surrounded by a variety of dining options, from casual eateries to upscale restaurants, where visitors can indulge in fresh seafood or enjoy a drink while watching the boats sail by. With its scenic views of the water and nearby coast, the marina creates a relaxing environment for both boat enthusiasts and those looking to unwind by the water. Its a perfect place to spend a leisurely day, whether you’re sailing, dining, or simply strolling along the waterfront.', '4.5', 'Akti Dimeon, Patras 262 22', '5', 'Open 24 hours', 38.26050541117799, 21.73845286419485),
                    (6, 'Salumeria Ristorante', 'This popular dining spot in Patras is a favorite for those seeking authentic Italian cuisine in a welcoming atmosphere. The menu features a range of traditional dishes, including handmade pasta, classic pizzas, and delectable seafood options, all crafted with high-quality ingredients. Complementing the food is an excellent wine list, featuring a selection of fine Italian wines as well as local Greek varieties. The restaurant’s cozy ambiance, attentive service, and focus on flavor make it a go-to destination for both locals and visitors looking to indulge in a memorable Italian dining experience in the heart of the city.', '4.6', 'Pantanassis 27, Patra 262 21', '5', '10:00 - 23:00', 38.24562047642444, 21.733425342014225),
                    (6, 'Argo', 'A highly recommended seafood restaurant in Patras, known for its fresh catches and stunning sea views, is the perfect spot for anyone craving authentic seafood dishes. Located near the coast, it offers a variety of freshly prepared seafood, including fish, calamari, octopus, and shellfish, all sourced from the local waters. The restaurants spacious terrace allows diners to enjoy their meals while taking in panoramic views of the sea, making for a truly memorable dining experience. Whether youre looking for a casual meal or a special occasion, the combination of fresh seafood and beautiful scenery makes it a must-visit in Patras.', '4.5', 'Ir. Politechniou 78, Patra 264 42', '5', '10:00 - 22:00', 38.26560201009002, 21.739799920534253),
                    (6, 'Rooster', 'This cozy bistro in Patras offers a delightful blend of Mediterranean flavors in a stylish and intimate setting. The menu features a range of dishes inspired by the vibrant cuisine of the region, including fresh seafood, grilled meats, and flavorful vegetable dishes, all prepared with high-quality, locally sourced ingredients. The atmosphere is warm and inviting, with tasteful décor and soft lighting that create the perfect ambiance for a relaxed meal or evening out. Whether youre enjoying a casual lunch or a romantic dinner, this bistro combines excellent food with a charming, sophisticated vibe, making it a standout dining spot.', '4.7', 'Trion Navarchon 39, Patra 262 22', '10', '12:00 - 23:00', 38.242341105620554, 21.731108546280122),
                    (6, 'Banana Moon', 'This relaxed beachside restaurant in Patras offers the perfect combination of fresh seafood and Mediterranean dishes, all served with a side of stunning views of the sea. With its laid-back atmosphere, the restaurant provides a great place to enjoy the flavors of the Mediterranean, from grilled fish and seafood platters to vibrant salads and traditional local dishes. The sound of the waves and the gentle sea breeze create an idyllic setting for a casual yet memorable dining experience. Whether youre enjoying a leisurely lunch or a sunset dinner, this beachside spot offers both delicious food and a serene environment.', '4.5', 'Paleon Patron Germanou 1b, Patra 262 25', '5', '10:00 - 22:00', 38.242969406534705, 21.73755196500798),
                    (7, 'Town Beach', 'A popular beach close to the city center in Patras, this spot is perfect for both swimming and relaxing. With its clear, inviting waters and clean sandy shoreline, it’s a favorite for locals and visitors looking to unwind by the sea. The beach offers easy access from the city, making it an ideal destination for a quick escape from the urban bustle. Whether youre looking to swim, sunbathe, or simply enjoy the view, the beach provides a peaceful environment. Nearby cafes and lounges offer refreshments, allowing for a full day of relaxation in a scenic, convenient setting.', '4.3', 'Patras', '0', 'Open 24 hours', 38.23822165210778, 21.724322197883843),
                    (7, 'Psathopyrgos Beach', 'This tranquil beach, located a bit further from the city, is the perfect escape for those seeking peace and solitude by the sea. With fewer crowds, the beach offers a serene atmosphere ideal for relaxing, reading, or simply enjoying the natural beauty of the surroundings. The gentle waves, clear water, and quiet ambiance create an idyllic environment for unwinding away from the hustle and bustle. Whether youre taking a long walk along the shoreline or just soaking up the sun, this beach is a hidden gem for a peaceful day by the sea.', '4.4', 'Psathopyrgos, Patras 265 00', '0', 'Open 24 hours', 38.29222594141814, 21.76578494463823),
                    (7, 'Rodini Beach', 'This beautiful beach, with its crystal-clear waters and soft sandy shore, is ideal for swimming and sunbathing. The calm, inviting waters make it a great spot for a refreshing swim, while the spacious beach provides plenty of room to relax and bask in the sun. The surrounding scenery adds to its charm, with the natural beauty of the area offering a perfect backdrop for a day by the sea. Whether youre looking to take a dip, enjoy the sun, or simply unwind in a picturesque setting, this beach offers an unforgettable experience.', '4.2', 'Rodini', '0', 'Open 24 hours', 38.3272099200996, 21.893353285254552),
                    (8, 'Disco Room', 'This popular nightclub in Patras is known for its vibrant atmosphere and energetic parties, making it a favorite among locals and visitors looking for a fun night out. The club regularly features live DJ performances, spinning the latest hits and creating an electric vibe that keeps the dance floor packed until the early hours. With its stylish décor, cutting-edge sound and light systems, and a wide selection of drinks, it’s the ultimate spot to enjoy a lively evening of dancing, socializing, and great music. Whether you’re looking to dance all night or enjoy the vibrant party scene, this nightclub delivers an unforgettable nightlife experience.', '4.3', 'Agiou Andreou 88, Patra 262 21', '10', '22:00 - 04:00', 38.247153138143275, 21.73268200741797),
                    (8, 'Frida Bar n’ Stage', 'This cozy bar and live music venue in Patras is the perfect spot to enjoy expertly crafted cocktails while soaking in local performances. The intimate setting creates a relaxed vibe, with live music adding to the atmosphere, whether it’s jazz, acoustic sets, or indie performances. The bar serves a variety of drinks, from signature cocktails to local wines and spirits, enhancing the experience. With its blend of great drinks and live entertainment, it’s an ideal place for a laid-back night out, offering both a welcoming space to unwind and the opportunity to enjoy the city’s local music scene.', '4.5', 'Gerokostopoulou 59, Patra 262 21', '5', '19:00 - 02:00', 38.244251525495926, 21.73767643744034),
                    (8, 'Drink & Go', 'This trendy bar in Patras is a hotspot for nightlife enthusiasts, offering a lively atmosphere that keeps the energy high throughout the night. With a great selection of drinks, from craft cocktails to fine spirits and local wines, it caters to all tastes. The sleek, modern décor adds to its appeal, providing a stylish backdrop for socializing with friends or meeting new people. The upbeat vibe, often complemented by music and occasional themed nights, makes it the perfect place to dance, enjoy a drink, and experience the vibrant nightlife of the city. It’s a must-visit for anyone looking to have a memorable night out.', '4.4', 'Gerokostopoulou 39, Patra 262 25', '10', '21:00 - 03:00', 38.24488315501826, 21.736924256711433),
                    (8, 'Café del Mar', 'This elegant bar, perched with a stunning view of the sea, offers the perfect blend of expertly crafted cocktails and a sophisticated atmosphere. The stylish setting, with its sleek décor and soft lighting, creates an intimate and relaxing vibe for evening outings. As the sun sets, guests can enjoy their drinks while taking in breathtaking views of the sea, making it a popular spot for unwinding after a long day or for a romantic night out. Whether you’re savoring a signature cocktail or a classic favorite, this bar provides the ultimate combination of great drinks, ambiance, and a mesmerizing sea view.', '2.0', 'Trion Navarchon 38, Patra 262 22', '5', '17:00 - 02:00', 38.24298731995191, 21.729237610691957)
                """)
            }
        } catch (e: Exception) {
            Log.e("Callback", "Error inserting initial data", e)
        }
    }
}