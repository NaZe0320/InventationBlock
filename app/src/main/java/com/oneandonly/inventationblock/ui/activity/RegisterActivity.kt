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
                            changeFragment(RegisterCompleteFragment(),"RegisterComplete")
                        }
                        "RegisterComplete" -> {
                            finish()
                        }
                    }
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