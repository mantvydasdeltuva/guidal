package com.guidal.data.db

import androidx.room.TypeConverter
import com.guidal.data.db.entities.UserEntity
import java.util.UUID

// TODO KDoc
// TODO Unit test
internal class Converters {
    @TypeConverter
    fun uuidToString(uuid: UUID?): String? {
        uuid?.let {
            if (uuid == UserEntity.DEFAULT_UUID) {
                return UUID.randomUUID().toString()
            }
            return uuid.toString()
        }
        return null
    }

    @TypeConverter
    fun stringToUUID(string: String?): UUID? {
        string?.let {
            return UUID.fromString(string)
        }
        return null
    }
}