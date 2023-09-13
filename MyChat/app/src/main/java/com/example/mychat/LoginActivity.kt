package com.example.mychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mychat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.showToast.observe(this, Observer {
            Toast.makeText(this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.isLoggedIn.observe(this, Observer {
            if(it){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}