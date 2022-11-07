package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityLoginBinding
import com.oneandonly.inventationblock.datasource.model.data.LoginState
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel
import com.oneandonly.inventationblock.viewmodel.LoginViewModel
import com.oneandonly.inventationblock.viewmodel.TokenViewModel
import com.oneandonly.inventationblock.viewmodel.factory.LoginViewModelFactory
import kotlinx.coroutines.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel

    private val autoLoginViewModel = AutoLoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val repository = LoginRepository()
        val viewModelFactory = LoginViewModelFactory(repository)

        loginViewModel = ViewModelProvider(this,viewModelFactory)[LoginViewModel::class.java]

        loginViewModel.loginResult.observe(this) {
            when (it) {
                LoginState.Loading -> {
                    Log.d("LoginCheck","Loading")

                    showLoading()
                }
                LoginState.Success -> {
                    Log.d("LoginCheck","Success")
                    stopLoading()
                    //TODO(토큰 저장)
                    moveToMain()
                }
                LoginState.Fail -> {
                    Log.d("LoginCheck","Fail")
                    stopLoading()
                    makeToast("아이디나 비밀번호를 화인해 주세요")
                }
                else -> {
                    Log.d("LoginCheck","Nothing")
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            autoLoginViewModel.updateAutoLogin(binding.cbAutoLogin.isChecked)
            Log.d("LoginCheck","login button click")
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    loginViewModel.postLogin(binding.editId.text.toString(),binding.editPw.text.toString())
                }
            }  catch (e : Exception) {
                Log.d("Login_Check","Error : ${e.message}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        autoLoginViewModel.getAutoLogin()
        if (autoLoginViewModel.isAutoLogin) moveToMain()
    }

    private fun showLoading() {
        //TODO(로딩창 생성 or 보여주기)
        Log.d("Loading","로딩창 보여주기")
    }

    private fun stopLoading() {
        //TODO(로딩창 닫기)
        Log.d("Loading","로딩창 닫기")
    }

    private fun moveToMain() {
        Log.d("Splash","moveToMain")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    } //메인 화면으로 이동

}