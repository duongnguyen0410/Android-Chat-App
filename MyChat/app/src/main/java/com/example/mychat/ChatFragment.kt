package com.example.mychat

import RecyclerViewAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychat.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: ChatFragmentViewModel
    private lateinit var userAdapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        viewModel = ViewModelProvider(this)[ChatFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        userAdapter = RecyclerViewAdapter(arrayListOf())
        binding.rvChatUsers.adapter = userAdapter
        binding.rvChatUsers.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveUser()
        viewModel.retrieveListUser()

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Log.d(TAG, "Main User: {${user.uid}} {${user.name}} {${user.email}} {${user.photoUrl}}")
        })

        viewModel.listUser.observe(viewLifecycleOwner, Observer { list ->
            userAdapter.setData(list)

            for (user in list){
                Log.d(TAG, "List User: {${user.uid}} {${user.name}} {${user.email}} {${user.photoUrl}}")
            }
        })
    }

    companion object {
        private const val TAG = "ChatFragment"
    }
}