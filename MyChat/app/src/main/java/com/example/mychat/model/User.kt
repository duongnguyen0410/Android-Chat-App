package com.example.mychat.model

import java.io.Serializable

data class User(val uid: String, val name: String, val email: String, val photoUrl: String): Serializable {
    constructor() : this("", "", "", "")
}
