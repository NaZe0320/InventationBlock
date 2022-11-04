package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityLoginBinding
import com.oneandonly.inventationblock.datasource.model.LoginModel
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.viewmodel.LoginViewModel
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        loginViewModel = LoginViewModel(this@LoginActivity)

        binding.btnLogin.setOnClickListener {
            loginViewModel.updateAutoLogin(binding.cbAutoLogin.isChecked)

            if (loginViewModel.loginCheck(loginModel = LoginModel(binding.editId.text.toString(),binding.editPw.text.toString()))){ //로그인 성공 시 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                makeToast("아이디나 비밀번호를 화인해 주세요")
            }
        }
    }

}