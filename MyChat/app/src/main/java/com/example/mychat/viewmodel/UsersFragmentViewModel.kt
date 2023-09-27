package com.example.mychat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.model.User
import com.example.mychat.repository.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UsersFragmentViewModel(private val repository: UserRepository) : ViewModel() {
    private val auth = Firebase.auth
    private val currentUser = auth.currentUser

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser : LiveData<ArrayList<User>>
        get() = _listUser

    private val _selectedUser = MutableLiveData<User>()
    val selectedUser : LiveData<User>
        get() = _selectedUser

    fun retrieveUser(){
        repository.retrieveUser(currentUser?.uid.toString()){ user ->
            _user.value = user
        }
    }

    fun retrieveListUser(){
        repository.retrieveListUsers { list ->
            _listUser.value = list
        }
    }

    fun handleUserClick(user: User){
        Log.i(TAG, "handleUserClick: ${user.name}")
        _selectedUser.value = user
    }

    companion object {
        private const val TAG = "UsersFragmentViewModel"
    }
}