package com.oneandonly.inventationblock.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.onEach

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loginViewModel = LoginViewModel(this)
        loginViewModel.getAutoLogin()
        if (loginViewModel.isAutoLogin) moveToMain() else moveToLogin()
    }

    private fun moveToLogin() {
        Log.d("Splash_","moveToLogin")
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun moveToMain() {
        Log.d("Splash","moveToMain")
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}