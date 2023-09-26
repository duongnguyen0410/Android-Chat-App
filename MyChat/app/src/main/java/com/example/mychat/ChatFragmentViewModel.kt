package com.example.mychat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.User.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatFragmentViewModel : ViewModel() {
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
}