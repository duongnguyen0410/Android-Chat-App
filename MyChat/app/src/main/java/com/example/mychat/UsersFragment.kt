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
import com.example.mychat.User.User
import com.example.mychat.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding
    private lateinit var viewModel: UsersFragmentViewModel
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)
        viewModel = ViewModelProvider(this)[UsersFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
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
            for (user in list){
                Log.d(TAG, "List User: {${user.uid}} {${user.name}} {${user.email}} {${user.photoUrl}}")
            }
        })
    }

    private fun initRecyclerView(){
        binding.rvChatUsers.layoutManager = LinearLayoutManager(this.context)
        adapter = RecyclerViewAdapter { userItem: User -> listItemClicked(userItem) }
        binding.rvChatUsers.adapter = adapter
        displayAllUsers()
    }

    private fun displayAllUsers(){
        viewModel.listUser.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(user: User) {
        viewModel.handleUserClick(user)
    }

    companion object {
        private const val TAG = "ChatFragment"
    }
}