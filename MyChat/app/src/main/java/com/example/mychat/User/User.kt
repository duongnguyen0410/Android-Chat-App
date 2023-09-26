package com.example.mychat.User

data class User(val uid: String, val name: String, val email: String, val photoUrl: String) {
    constructor() : this("", "", "", "")
}
