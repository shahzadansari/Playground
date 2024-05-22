package com.example.playground.mock

import com.example.playground.tutorial01_typesafe_compose_navigation.User

val User.Companion.mock by lazy {
    User(name = "John", age = 25)
}