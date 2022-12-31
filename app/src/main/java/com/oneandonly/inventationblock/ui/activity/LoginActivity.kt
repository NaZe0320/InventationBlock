package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityLoginBinding
import com.oneandonly.inventationblock.datasource.model.data.LoginState
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel
import com.oneandonly.inventationblock.viewmodel.LoginViewModel
import com.oneandonly.inventationblock.viewmodel.TokenViewModel
import com.oneandonly.inventationblock.viewmodel.factory.LoginFactory
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel

    //Setting
    private val autoLoginViewModel = AutoLoginViewModel()
    private val tokenViewModel = TokenViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val repository = LoginRepository()
        val viewModelFactory = LoginFactory(repository)

        loginViewModel = ViewModelProvider(this,viewModelFactory)[LoginViewModel::class.java]
        Log.d("LoginViewModel Test","${loginViewModel.loginResult}/${loginViewModel.token}")

        loginViewModel.loginResult.observe(this) {
            when (it) {
                LoginState.Loading -> {
                    Log.d("LoginCheck","loading")

                    showLoading()
                }
                LoginState.Success -> {
                    Log.d("LoginCheck","Success")
                    stopLoading()

                    autoLoginViewModel.updateAutoLogin(binding.cbAutoLogin.isChecked)

                    tokenViewModel.updateToken(loginViewModel.token.value.toString())
                    moveToMain()

                    loginViewModel.loginResult.value = LoginState.Null
                }
                LoginState.Fail -> {
                    Log.d("LoginCheck","Fail")
                    stopLoading()
                    makeToast("아이디나 비밀번호를 화인해 주세요")

                    loginViewModel.loginResult.value = LoginState.Null
                }
                else -> {
                    Log.d("LoginCheck","Nothing")
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.loginResult.value = LoginState.Loading //로그인 중
            onClickLogin()
        }

        binding.btnRegister.setOnClickListener {
            moveToRegister()
        }

        binding.btnSearchIdpw.setOnClickListener {
            //TODO(아이디 비밀번호 찾기)
        }

        Log.d("Token Login",tokens.toString())
    }

    override fun onStart() {
        super.onStart()
        autoLoginViewModel.getAutoLogin()
        if (autoLoginViewModel.isAutoLogin){
            moveToMain()
        }
    }

    private fun showLoading() {
        //TODO(로딩창 생성 or 보여주기)
        binding.btnLogin.apply {
            isEnabled = false
        }

        Log.d("loading","로딩창 보여주기")
    }

    private fun stopLoading() {
        //TODO(로딩창 닫기)
        binding.btnLogin.apply {
            isEnabled = true
        }
        Log.d("loading","로딩창 닫기")
    }

    private fun moveToMain() {
        Log.d("Splash","moveToMain")
        tokenViewModel.getToken()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    } //메인 화면으로 이동

    private fun moveToRegister() {
        Log.d("Splash","moveToRegister")
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun onClickLogin() {
        Log.d("Login_Check","login button click")
        try {
            CoroutineScope(Dispatchers.Main).launch {
                loginViewModel.postLogin(binding.editId.text.toString(),binding.editPw.text.toString())
            }
        }  catch (e : Exception) {
            Log.d("Login_Check","Error : ${e.message}")
        }
    }

}