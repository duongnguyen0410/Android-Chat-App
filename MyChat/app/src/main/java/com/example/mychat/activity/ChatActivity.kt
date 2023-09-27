package com.example.mychat.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychat.model.User
import com.example.mychat.R
import com.example.mychat.adapter.ChatAdapter
import com.example.mychat.databinding.ActivityChatBinding
import com.example.mychat.factory.ChatViewModelFactory
import com.example.mychat.repository.ChatRepository
import com.example.mychat.viewmodel.ChatActivityViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatActivityViewModel
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        val selectedUser = intent.getSerializableExtra("user") as? User
        binding.tvSelectedUserName.text = selectedUser?.name

        val chatRepository = ChatRepository()
        val factory = ChatViewModelFactory(chatRepository, selectedUser!!.uid)
        viewModel = ViewModelProvider(this, factory)[ChatActivityViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvChat.layoutManager = LinearLayoutManager(this)

        viewModel.retrieveMessage()

        viewModel.toast.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.rvChat.layoutManager = layoutManager
        chatAdapter = ChatAdapter(this, ArrayList())
        binding.rvChat.adapter = chatAdapter
        displayChat()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayChat() {
        viewModel.chatList.observe(this, Observer {
            Log.d(TAG, "displayChat: ${it.toString()}")
            chatAdapter.setList(it)
            chatAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}