package com.oneandonly.inventationblock.ui.fragment

import android.graphics.Typeface
import android.graphics.Typeface.*
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.Spanned.*
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.Constants
import com.oneandonly.inventationblock.databinding.FragmentRegisterBinding
import com.oneandonly.inventationblock.datasource.model.data.RegisterModel
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.ui.adapter.RegisterAdapter
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory

class RegisterFragment: ContainerFragment() {

    companion object {
        fun newInstance() = RegisterFragment
    }

    private var _binding: FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    private lateinit var userVM: UserViewModel

    private lateinit var registerList: ArrayList<RegisterModel>

    private var registerAdapter: RegisterAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val view = binding.root

        val repo = UserRepository()
        val vmFactory = UserFactory(repo)

        userVM = ViewModelProvider(this@RegisterFragment, vmFactory)[UserViewModel::class.java]

        registerList = Constants.registerList()
        textBold() //텍스트 Bold 처리

        setupRegisterRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userVM.state.observe(viewLifecycleOwner) {
            //TODO(회원가입 상태 확인)
       }
    }



    private fun textBold() {
        val builder = SpannableStringBuilder(binding.textRegister.text)
        val boldSpan = StyleSpan(BOLD)
        builder.setSpan(boldSpan,11,15, SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textRegister.text = builder
    }

    private fun setupRegisterRecyclerView(){
        binding.rvRegister.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false )

        registerAdapter = RegisterAdapter(registerList)
        binding.rvRegister.adapter = registerAdapter
    }
}