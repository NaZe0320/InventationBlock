package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityRegisterBinding
import com.oneandonly.inventationblock.datasource.model.data.RegisterModel
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.ui.adapter.RegisterAdapter
import com.oneandonly.inventationblock.ui.fragment.RegisterCompleteFragment
import com.oneandonly.inventationblock.ui.fragment.RegisterFragment
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var userVM: UserViewModel


    //TEST 코드
    private val registerList : ArrayList<RegisterModel> = ArrayList()
    private lateinit var registerAdapter: RegisterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)

        val repo = UserRepository()
        val vmFactory = UserFactory(repo)

        userVM = ViewModelProvider(this@RegisterActivity, vmFactory)[UserViewModel::class.java]

        changeFragment(RegisterFragment(),"Register") //TODO(TEST)

        binding.btnRegister.setOnClickListener {
            for (fragment: Fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible) {
                    when (fragment.tag) {
                        "Register" -> {
                            Log.d("Register_Activity", "${userVM.state.value}")
                            userVM.state.value = State.Loading //진행 중 상태로 변경

                        }
                        "RegisterComplete" -> {
                            finish()
                        }
                    }
                }
            }
        /*TODO(버튼 클릭 시 회원가입 정보를 Fragment로부터 받아와서 (ViewModel에서 처리하면 될 듯하다.
           회원가입 성공일 때 State를 Success로 변경하여 다음 화면으로 넘어가기
           ViewModel에서 Model과 통신하기)*/

        }
    }

    override fun onStart() {
        super.onStart()
        userVM.state.observe(this) { it ->
            when (it) {
                State.Success -> {
                    //changeFragment(RegisterCompleteFragment(),"RegisterComplete")
                }
                State.Fail -> {
                    
                }
                State.Loading -> {
                    //TODO(회원가입 신청)

                }
            }
        }
    }
    private fun changeFragment(fragment: Fragment, tag: String?=null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_register, fragment, tag)
        transaction.commit()
    }

}