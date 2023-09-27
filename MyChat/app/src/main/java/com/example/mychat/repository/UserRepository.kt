package com.example.mychat.repository

import android.util.Log
import com.example.mychat.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val auth = Firebase.auth
    private val database = Firebase.database
    private val ref = database.reference.child("users")

    fun insertUser(user: FirebaseUser, callback: (Boolean) -> Unit) {
        val newUser = User(user.uid, user.displayName.toString(), user.email.toString(), user.photoUrl.toString())
        ref.child(user.uid).setValue(newUser)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Log.d(TAG, "insertUser: success")
                    callback(true)
                } else {
                    Log.d(TAG, "insertUser: failed, ${it.exception}")
                    callback(false)
                }
            }
    }

    fun retrieveUser(uid: String, callback: (User) -> Unit) {
        ref.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        callback(user)
                    }
                } else {
                    Log.e(TAG, "onDataChange: User not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "retrieveUser: failed, ${databaseError.message}")
            }
        })
    }

    fun retrieveListUsers(callback: (ArrayList<User>) -> Unit) {
        val list = ArrayList<User>()
        val currentUser = auth.currentUser
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (data in dataSnapshot.children) {
                    val user = data.getValue(User::class.java)
                    if (user != null && user.uid != currentUser?.uid) {
                        list.add(user)
                    }
                }
                callback(list)
                Log.d(TAG, "retrieveListUsers: ${list.size}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "retrieveListUsers: failed, ${databaseError.message}")
            }
        })
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}