package com.example.cleanarchitecture.data.mapper

import com.example.cleanarchitecture.data.remote.dto.UserDto
import com.example.cleanarchitecture.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id,
    name = name.orEmpty(),
    username = username.orEmpty(),
    email = email.orEmpty(),
    phone = phone.orEmpty(),
    website = website.orEmpty(),
    city = address?.city.orEmpty(),
    company = company?.name.orEmpty(),
)

fun List<UserDto>.toDomain(): List<User> = map { it.toDomain() }
