package com.oneandonly.inventationblock.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.databinding.FragmentRegisterBinding
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentRegister: ContainerFragment() {

    companion object {
        fun newInstance() = FragmentRegister()
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        val repository = UserRepository()
        val viewModelFactory = UserFactory(repository)

        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]


        val builder = SpannableStringBuilder(binding.textRegister.text)
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan,11,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textRegister.text = builder

        binding.btnRegister.setOnClickListener {
            onClickRegister()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                State.Success -> {
                    
                }
                State.Fail -> {

                }
                State.Loading -> {

                }
                else -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClickRegister() {
        val params = HashMap<String, String>()
        params["id"] = binding.editRegId.text.toString()
        params["password"] = binding.editRegPw.text.toString()
        params["passwordCheck"] = binding.editRegPwCheck.text.toString()
        params["name"] = binding.editRegName.text.toString()
        params["email"] = binding.editRegEmail.text.toString()
        params["businessName"] = binding.editRegBusinessName.text.toString()
        params["businessNum"] = binding.editRegBusinessNum.text.toString()

        try {
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.postRegister(params)
            }
        } catch (e: Exception) {
            Log.d("FragmentRegister","Error : ${e.message}")
        }

    }


}