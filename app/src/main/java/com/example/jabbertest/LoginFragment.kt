package com.example.jabbertest

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.jabbertest.databinding.FragLoginBinding
import kotlinx.android.synthetic.main.frag_login.*
import timber.log.Timber


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragLoginBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        // Navigation
        viewModel.navToChats.subscribeToNavigation(
            owner = this,
            actionsBeforeNavigation = {
                viewModel.captureAccount()
            },
            navigation = {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToChatsFragment()
                )
            },
            resetBool = { viewModel.onNavigatedToChats() }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d(SharedPref.getString("data", "No Data :("))
        accountSelection()
    }

    private fun accountSelection() {
        saveAccount("${rb_one.text}")
        rb_group_accounts.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId) {
                rb_one.id -> saveAccount("${rb_one.text}")
                rb_two.id -> saveAccount("${rb_two.text}")
                rb_three.id -> saveAccount("${rb_three.text}")
                rb_four.id -> saveAccount("${rb_four.text}")
                rb_five.id -> saveAccount("${rb_five.text}")
                rb_six.id -> saveAccount("${rb_six.text}")
                rb_seven.id -> saveAccount("${rb_seven.text}")
                rb_eight.id -> saveAccount("${rb_eight.text}")
            }
        }
    }
    private fun saveAccount(account: String) {
        Timber.d("Account selected is - $account")
        viewModel.accountName(account)
    }
}