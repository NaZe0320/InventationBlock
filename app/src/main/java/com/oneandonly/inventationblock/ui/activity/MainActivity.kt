package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMainBinding
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.main = this

        binding.btnLogin.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        val autoLoginViewModel = AutoLoginViewModel()
        autoLoginViewModel.updateAutoLogin(false)
        moveToLogin()
    }

    private fun moveToLogin() {
        Log.d("Splash_","moveToLogin")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}