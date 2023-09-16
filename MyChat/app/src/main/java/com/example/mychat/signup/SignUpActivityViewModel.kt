package com.example.mychat.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychat.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivityViewModel : ViewModel(){
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    private var _isUpdateDone = false
    private val _isSignUpDone = MutableLiveData<Boolean>()

    val isSignUpDone: LiveData<Boolean>
        get() = _isSignUpDone

    private val _showToast = MutableLiveData<String>()
    val showTask: LiveData<String>
        get() = _showToast

    private val auth = Firebase.auth
    private val databaseReference = Firebase.database.reference

    private val userRepo = UserRepository()

    init {
        _isSignUpDone.value = false
    }

    fun signup(){
        if (validate()){
            auth.createUserWithEmailAndPassword(email.value.toString(), password.value.toString())
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val user = task.result?.user
                        if (user != null){
                            pushUserInfoToDatabase(user)
                        }

                    } else {
                        _showToast.value = "Sign up failed."
                        Log.w(TAG, "createUserWithEmail: failed", task.exception)
                    }
                }
        } else {
            _showToast.value = "Field is required"
        }
    }

    private fun validate(): Boolean{
        return !(email.value == null || password.value == null || name.value == null)
    }

    private fun pushUserInfoToDatabase(user: FirebaseUser){
        val profileUpdates = userProfileChangeRequest {
            displayName = name.value.toString()
        }

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "updateUserInfo: success")
                    val updatedUser = auth.currentUser
                    userRepo.insertUser(updatedUser!!)
                } else {
                    Log.w(TAG, "updateUserInfo: failed", task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "SignupActivity"
    }
}