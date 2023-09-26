package com.example.mychat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.User.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UsersFragmentViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _listUser = MutableLiveData<ArrayList<User>>()

    val listUser : LiveData<ArrayList<User>>
        get() = _listUser

    private val userRepo = UserRepository()

    private val auth: FirebaseAuth = Firebase.auth
    private val currentUser = auth.currentUser

    fun retrieveUser(){
        userRepo.retrieveUser(currentUser?.uid.toString()){ user ->
            _user.value = user
        }
    }

    fun retrieveListUser(){
        userRepo.retrieveListUsers { list ->
            _listUser.value = list
        }
    }

    fun handleUserClick(user: User){
        if(user.name == "An Vu"){
            Log.i(TAG, "handleUserClick: GAYYYYYY")
        } else {
            Log.i(TAG, "handleUserClick: ${user.name}")
        }
    }

    companion object {
        private const val TAG = "UsersFragmentViewModel"
    }
}