package com.oneandonly.inventationblock.ui.fragment

import android.graphics.Typeface.*
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned.*
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentRegisterBinding
import com.oneandonly.inventationblock.datasource.model.data.RegisterModel
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.ui.adapter.RegisterAdapter
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment: ContainerFragment() {

    companion object {
        fun newInstance() = RegisterFragment
    }

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var userVM: UserViewModel

    private var registerList: ArrayList<RegisterModel> = ArrayList()
    private lateinit var registerAdapter: RegisterAdapter

    private val params = HashMap<String,String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false)
        val view = binding.root

        val repo = UserRepository()
        val vmFactory = UserFactory(repo, requireActivity())

        userVM = ViewModelProvider(requireActivity(), vmFactory)[UserViewModel::class.java]

        textBold() //텍스트 Bold 처리

        setupRegisterRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userVM.state.observe(viewLifecycleOwner) {
            //TODO(회원가입 상태 확인)
            when (it) {
                State.Loading -> {
                    registerList.forEachIndexed { _, register ->
                        Log.d("Register_Fragment","${register.hint} - ${register.content}")
                        params[register.id] = register.content
                    }

                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                            userVM.postRegister(params)
                        }
                    } catch (e: Exception) {
                        Log.d("FragmentRegister","Error : ${e.message}")
                    }
                    Log.d("Register_Fragment","$params")
                }
                State.Success -> {
                    //TODO(회원가입 성공)
                }
                State.Fail -> {
                    //TODO(회원가입 실패)
                    //회원가입 실패 원인 받아서 해당 이유 표시해주기
                    //원인 viewmodel reason 체크
                    when (userVM.reason) {

                    }
                }
                State.Null -> {

                }
                else -> {}
            }

        }
    }

    private fun textBold() {
        val builder = SpannableStringBuilder(binding.textRegister.text)
        val boldSpan = StyleSpan(BOLD)
        builder.setSpan(boldSpan,11,15, SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textRegister.text = builder
    }

    private fun setupRegisterRecyclerView(){
        setRegisterList()
        registerAdapter = RegisterAdapter(registerList = registerList)

        binding.rvRegister.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false )
        binding.rvRegister.adapter = registerAdapter
    }

    private fun setRegisterList() {
        registerList.add(RegisterModel("id","아이디",InputType.TYPE_CLASS_TEXT, comment = "아이디"))
        registerList.add(RegisterModel("password","비밀번호",InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,comment = "비밀번호 * 특수문자 + 영소문자 + 숫자로 이루어진 8자리 이상"))
        registerList.add(RegisterModel("passwordCheck","비밀번호 확인",InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD, comment = "비밀번호 확인"))
        registerList.add(RegisterModel("name","사용자명",InputType.TYPE_CLASS_TEXT, comment = "사용자명"))
        registerList.add(RegisterModel("email","이메일",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, comment = "이메일"))
        registerList.add(RegisterModel("businessName","사업자명",InputType.TYPE_CLASS_TEXT ,comment = "사업자명"))
        registerList.add(RegisterModel("businessNum","사업자등록번호",InputType.TYPE_CLASS_NUMBER, comment = "사업자등록번호 (10자리)"))

        //TODO(비밀번호 형식 지정안됨 수정 필요요)
    }
}