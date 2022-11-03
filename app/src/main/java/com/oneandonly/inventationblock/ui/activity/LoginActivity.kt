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

class LoginActivity : AppCompatActivity() {

    private var loginBinding: ActivityLoginBinding?= null
    private val binding get() = loginBinding!!

    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            if (loginViewModel.loginCheck(LoginModel(binding.editId.text.toString(),binding.editPw.text.toString()))){ //로그인 성공 시 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                makeToast("아이디나 비밀번호를 화인해 주세요")
            } //TODO(NullExceptionError 발생하는지 확인하고 밠상하면 예외처리)
        }
    }

}