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

    fun retrieveUser(uid: String, callback: (User) -> Unit){
        ref.child(uid).get().addOnCompleteListener {
            if (it.isSuccessful){
                val user = it.result?.getValue(User::class.java)
                if (user != null){
                    callback(user)
                }
            } else {
                Log.d(TAG, "retrieveUser: failed, ${it.exception}")
            }
        }
    }

    fun retrieveListUsers(callback: (ArrayList<User>) -> Unit){
        val list = ArrayList<User>()
        val currentUser = auth.currentUser
        ref.get().addOnCompleteListener {
            if (it.isSuccessful){
                for (data in it.result.children){
                    val user = data.getValue(User::class.java)
                    if (user != null && user.uid != currentUser?.uid){
                        list.add(user)
                    }
                }
                callback(list)
                Log.d(TAG, "retrieveListUsers: ${list.size}")
            } else {
                Log.d(TAG, "retreiveList: failed, ${it.exception}")
            }
        }
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}