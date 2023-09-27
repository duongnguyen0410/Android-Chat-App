package com.example.mychat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mychat.R
import com.example.mychat.viewmodel.LoginActivityViewModel
import com.example.mychat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginActivityViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

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