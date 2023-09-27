package com.example.mychat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mychat.R
import com.example.mychat.databinding.ActivitySignUpBinding
import com.example.mychat.viewmodel.SignUpActivityViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this)[SignUpActivityViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.showTask.observe(this, Observer {
            Toast.makeText(this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.isSignUpDone.observe(this, Observer { result ->
            if(result){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }
}