package com.example.mychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mychat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        Log.d(TAG, "user: ${auth.currentUser?.email.toString()}")

        binding.btnSignOut.setOnClickListener {
            signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signOut(){
        auth.signOut()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}