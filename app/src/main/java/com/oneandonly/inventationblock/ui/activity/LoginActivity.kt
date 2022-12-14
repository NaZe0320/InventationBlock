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
                    makeToast("???????????? ??????????????? ????????? ?????????")

                    loginViewModel.loginResult.value = LoginState.Null
                }
                else -> {
                    Log.d("LoginCheck","Nothing")
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.loginResult.value = LoginState.Loading //????????? ???
            onClickLogin()
        }

        binding.btnRegister.setOnClickListener {
            moveToRegister()
        }

        binding.btnSearchIdpw.setOnClickListener {
            //TODO(????????? ???????????? ??????)
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
        //TODO(????????? ?????? or ????????????)
        binding.btnLogin.apply {
            isEnabled = false
        }

        Log.d("loading","????????? ????????????")
    }

    private fun stopLoading() {
        //TODO(????????? ??????)
        binding.btnLogin.apply {
            isEnabled = true
        }
        Log.d("loading","????????? ??????")
    }

    private fun moveToMain() {
        Log.d("Splash","moveToMain")
        tokenViewModel.getToken()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    } //?????? ???????????? ??????

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