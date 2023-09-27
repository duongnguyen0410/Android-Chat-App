package com.example.mychat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mychat.fragment.ProfileFragment
import com.example.mychat.R
import com.example.mychat.fragment.UsersFragment
import com.example.mychat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(UsersFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
             when(it.itemId){
                 R.id.chat -> replaceFragment(UsersFragment())
                 R.id.profile -> replaceFragment(ProfileFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}