package com.guidal.data.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.guidal.core.utils.orDefault
import com.guidal.data.db.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

// TODO KDoc when finalized
// TODO (Unit?Android) test

val Context.dataStore by preferencesDataStore(name = "user_prefs")

val USER_ID_KEY = stringPreferencesKey("user_id")
val USER_NAME_KEY = stringPreferencesKey("user_name")
val USER_SURNAME_KEY = stringPreferencesKey("user_surname")
val USER_EMAIL_KEY = stringPreferencesKey("user_email")
val USER_GENDER_KEY = stringPreferencesKey("user_gender")
val USER_COUNTRY_KEY = stringPreferencesKey("user_country")
val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
val USER_ROLE_KEY = stringPreferencesKey("user_role")

suspend fun saveUserToDataStore(context: Context, user: UserModel) {
    context.dataStore.edit { preferences ->
        preferences[USER_ID_KEY] = user.id.toString().orDefault("")
        preferences[USER_NAME_KEY] = user.name
        preferences[USER_SURNAME_KEY] = user.surname.orDefault("")
        preferences[USER_EMAIL_KEY] = user.email
        preferences[USER_GENDER_KEY] = user.gender.orDefault("")
        preferences[USER_COUNTRY_KEY] = user.country.orDefault("")
        preferences[USER_PASSWORD_KEY] = user.password
        preferences[USER_ROLE_KEY] = user.role.orDefault("")
    }
}

fun getUserFromDataStore(context: Context): Flow<UserModel> {
    return context.dataStore.data
        .map { preferences ->
            val id = UUID.fromString(preferences[USER_ID_KEY] ?: "")
            val name = preferences[USER_NAME_KEY] ?: ""
            val surname = preferences[USER_SURNAME_KEY] ?: ""
            val email = preferences[USER_EMAIL_KEY] ?: ""
            val gender = preferences[USER_GENDER_KEY] ?: ""
            val country = preferences[USER_COUNTRY_KEY] ?: ""
            val password = preferences[USER_PASSWORD_KEY] ?: ""
            val role = preferences[USER_ROLE_KEY] ?: ""
            UserModel(id, name, surname, email, gender, country, password, role)
        }
}
