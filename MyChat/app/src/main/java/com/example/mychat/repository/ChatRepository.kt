package com.example.mychat.repository

import android.util.Log
import com.example.mychat.model.Chat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class ChatRepository {
    private val auth = Firebase.auth
    private val database = Firebase.database
    private val ref = database.reference.child("chats")
    private val currentUser = auth.currentUser

    fun sendMessage(message: String?, receiverId: String, callback: (Boolean) -> Unit) {
        val senderId = currentUser?.uid.toString()
        val chat = Chat(senderId, receiverId, message!!)
        ref.child(currentUser?.uid.toString() + "-" + receiverId).push().setValue(chat)
            .addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    ref.child(receiverId + "-" + currentUser?.uid.toString()).push().setValue(chat)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                callback(true)
                            } else {
                                callback(false)
                                Log.e(TAG, "sendMessage: failed - ${task2.exception}")
                            }
                        }
                } else {
                    callback(false)
                    Log.e(TAG, "sendMessage: failed - ${task1.exception}")
                }
            }
    }

    fun retrieveMessage(receiverId: String, callback: (ArrayList<Chat>) -> Unit) {
        val list = ArrayList<Chat>()
        ref.child(currentUser?.uid.toString() + "-" + receiverId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (data in dataSnapshot.children) {
                    val chat = data.getValue(Chat::class.java)
                    Log.d(TAG, "onDataChange: {${chat.toString()}}")
                    if (chat != null) {
                        list.add(chat)
                    }
                }
                Log.d(TAG, "onDataChange: {${list.toString()}}")
                callback(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "retrieveMessage: failed, ${databaseError.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ChatActivityRepository"
    }
}