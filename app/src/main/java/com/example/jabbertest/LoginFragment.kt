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
            actionsBeforeNavigation = {},
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
        rb_group_accounts.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId) {
                rb_one.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_two.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_three.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_four.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_five.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_six.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_seven.id -> Timber.d("Changed Radiobutton value - $checkedId")
                rb_eight.id -> Timber.d("Changed Radiobutton value - $checkedId")
            }
        }
    }

    fun accountSelected(v: View) {
        Timber.d("The radio button that was clicked is - ${v.id}")
    }
}