package com.example.mychat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychat.R
import com.example.mychat.model.Chat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter (private val context: Context, private val chatList: ArrayList<Chat>): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {
    private val currentUser = Firebase.auth.currentUser
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
            return MyViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_left, parent, false)
            return MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chat = chatList[position]
        holder.tvMessage.text = chat.message
    }

    fun setList(chats: List<Chat>){
        chatList.clear()
        chatList.addAll(chats)
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].senderId == currentUser?.uid){
            MESSAGE_TYPE_RIGHT
        } else {
            MESSAGE_TYPE_LEFT
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    companion object {
        private const val MESSAGE_TYPE_LEFT = 0
        private const val MESSAGE_TYPE_RIGHT = 1
    }
}

