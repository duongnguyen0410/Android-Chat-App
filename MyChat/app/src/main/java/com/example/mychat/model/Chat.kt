package com.example.mychat.model

data class Chat(val senderId: String, val receiverId: String, val message: String){
    constructor() : this("", "", "")
}
