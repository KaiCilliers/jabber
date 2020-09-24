package com.example.jabbertest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jabbertest.databinding.FragChatsBinding
import timber.log.Timber

class ChatsFragment : Fragment() {
    private lateinit var viewModel: ChatsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragChatsBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(SharedPref.getString("com.example.jabbertest.account", "No account..."))
    }
}