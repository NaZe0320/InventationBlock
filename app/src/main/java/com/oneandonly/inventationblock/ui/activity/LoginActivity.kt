package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityLoginBinding
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel
import com.oneandonly.inventationblock.viewmodel.LoginViewModel
import com.oneandonly.inventationblock.viewmodel.factory.LoginViewModelFactory
import kotlinx.coroutines.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        val autoLoginViewModel = AutoLoginViewModel()

        val repository = LoginRepository()
        val viewModelFactory = LoginViewModelFactory(repository)

        loginViewModel = ViewModelProvider(this,viewModelFactory)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            autoLoginViewModel.updateAutoLogin(binding.cbAutoLogin.isChecked)

            try {
                val params = HashMap<String, String>()
                params["id"] = binding.editId.text.toString()
                params["password"] = binding.editPw.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    loginViewModel.postLogin(params)
                    //TODO(비동기 처리로 postLogin 이 완료 된 후에 result 값을 호출하는 방식으로 변경)
                    //TODO(makeToast 오류 남)
                    delay(1500L)
                    if (loginViewModel.result){ //로그인 성공 시 이동
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        makeToast("아이디나 비밀번호를 화인해 주세요")
                    }
                }

            }  catch (e : Exception) {
                Log.d("Login_Check","Error : ${e.message}")
            }
        }

    }





}