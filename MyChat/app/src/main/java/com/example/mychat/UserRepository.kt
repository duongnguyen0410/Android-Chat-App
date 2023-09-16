package com.example.mychat

import android.util.Log
import com.example.mychat.User.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val auth = Firebase.auth
    private val database = Firebase.database
    private val ref = Firebase.database.reference.child("users")

    fun insertUser(user: FirebaseUser) {
        val newUser = User(user.uid, user.displayName.toString(), user.email.toString(), user.photoUrl.toString())
        ref.child(user.uid).setValue(newUser)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Log.d(TAG, "insertUser: success")
                    auth.signOut()
                } else {
                    Log.d(TAG, "insertUser: failed, ${it.exception}")
                }
            }
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}