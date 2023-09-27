package com.example.mychat.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mychat.activity.ChatActivity
import com.example.mychat.repository.ChatRepository
import com.example.mychat.viewmodel.ChatActivityViewModel

class ChatViewModelFactory(private val repository: ChatRepository, private val receiverId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatActivityViewModel::class.java)) {
            return ChatActivityViewModel(repository, receiverId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}