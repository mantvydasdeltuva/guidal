package com.guidal.data.db.models

import com.guidal.data.db.daos.CountryDao
import com.guidal.data.db.daos.GenderDao
import com.guidal.data.db.daos.RoleDao
import com.guidal.data.db.entities.CountryEntity
import com.guidal.data.db.entities.GenderEntity
import com.guidal.data.db.entities.RoleEntity
import com.guidal.data.db.entities.UserEntity
import java.util.UUID

// TODO KDoc
// TODO Unit test
data class UserModel(
    val id: UUID? = null,
    val name: String,
    val surname: String? = null,
    val email: String,
    val gender: String? = null,
    val country: String? = null,
    val password: String,
    val role: String? = null
) {
    internal suspend fun toUserEntity(genderDao: GenderDao, countryDao: CountryDao, roleDao: RoleDao): UserEntity {
        val id = id ?: UserEntity.DEFAULT_UUID

        val surname = surname ?: UserEntity.DEFAULT_SURNAME

        val genderId = gender?.let {
            genderDao.getGenderByName(gender)?.id
                ?: throw IllegalArgumentException("Invalid gender.")
        } ?: GenderEntity.DEFAULT_GENDER_ID

        val countryId = country?.let {
            countryDao.getCountryByName(country)?.id
                ?: throw IllegalArgumentException("Invalid country.")
        } ?: CountryEntity.DEFAULT_COUNTRY_ID

        val roleId = role?.let {
            roleDao.getRoleByName(role)?.id
                ?: throw IllegalArgumentException("Invalid role.")
        } ?: RoleEntity.DEFAULT_ROLE_ID

        return UserEntity(
            id = id,
            name = name,
            surname = surname,
            email = email,
            genderId = genderId,
            countryId = countryId,
            password = password,
            roleId = roleId
        )
    }
}