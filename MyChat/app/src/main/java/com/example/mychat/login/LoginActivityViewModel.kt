package com.example.mychat.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivityViewModel : ViewModel() {
    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    private val auth = Firebase.auth

    init {
        checkUserLoginStatus()
    }

    fun logIn(){
        if(validate()){
            auth.signInWithEmailAndPassword(email.value.toString(), password.value.toString())
                .addOnCompleteListener { task ->
                    _isUserLoggedIn.value = task.isSuccessful
                }
        } else {
            _showToast.value = "Empty username or password"
        }
    }

    private fun checkUserLoginStatus(){
        _isUserLoggedIn.value = (auth.currentUser != null)
    }

    private fun validate(): Boolean {
        return !(email.value == null || !Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches() || password.value == null)
    }
}