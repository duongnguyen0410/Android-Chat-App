package com.example.mychat.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.model.Chat
import com.example.mychat.repository.ChatRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatActivityViewModel(private val repository: ChatRepository, private val receiverId: String): ViewModel() {
    val message = MutableLiveData<String>()

    private val _chatList = MutableLiveData<ArrayList<Chat>>()
    val chatList: LiveData<ArrayList<Chat>>
        get() = _chatList

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast
    fun btnSendOnClick() {
        if (textValidate()){
            repository.sendMessage(message.value, receiverId, callback = { result ->
                if (result){
                    message.value = ""
                } else {
                    _toast.value = "Failed to send message"
                    Log.e(TAG, "btnSendOnClick: Failed!")
                }
            })
        }
    }

    fun retrieveMessage() {
        repository.retrieveMessage(receiverId, callback = { list ->
            _chatList.value = list
            Log.d(TAG, "retrieveMessage: {${list.toString()}}")
        })
    }

    private fun textValidate(): Boolean {
        return if (TextUtils.isEmpty(message.value.toString())){
            _toast.value = "Field is required"
            false
        } else {
            true
        }
    }

    companion object {
        private const val TAG = "ChatActivityViewModel"
    }
}