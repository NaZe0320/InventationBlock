package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityRegisterBinding
import com.oneandonly.inventationblock.ui.fragment.RegisterCompleteFragment
import com.oneandonly.inventationblock.ui.fragment.RegisterFragment

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(RegisterFragment(),"Register")

        binding.btnRegister.setOnClickListener {
            for(fragment:Fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible) {
                    when(fragment.tag) {
                        "Register" -> {
                            Log.d("Register_Activity","${supportFragmentManager.fragments}")

                            if (true) { //TODO(성공했을 때)
                                changeFragment(RegisterCompleteFragment(),"RegisterComplete")
                            } else {
                            }

                        }
                        "RegisterComplete" -> {
                            finish()
                        }
                    }
                }
            }
            /*TODO(버튼 클릭 시 회원가입 정보를 Fragment로부터 받아와서 (ViewModel에서 처리하면 될 듯하다.
               회원가입 성공일 때 State를 Success로 변경하여 다음 화면으로 넘어가기
               ViewModel에서 Model과 통신하기)
            */

        }

    }

    private fun changeFragment(fragment: Fragment, tag: String?=null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_register, fragment, tag)
        transaction.commit()
    }
}