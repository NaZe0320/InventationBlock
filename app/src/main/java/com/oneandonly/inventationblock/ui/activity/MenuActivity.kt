package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMenuBinding
import com.oneandonly.inventationblock.ui.fragment.MenuAddFragment
import com.oneandonly.inventationblock.ui.fragment.RegisterFragment

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MenuActivity, R.layout.activity_menu)
        binding.lifecycleOwner = this@MenuActivity

        if (savedInstanceState == null) {
            fragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fl_menu, MenuAddFragment(), "Menu")
                add(R.id.fl_menu, MenuAddFragment(), "Drink")
                addToBackStack(null)
            }
        }

        setViewModel()
        uiSetting()
    }

    private fun setViewModel() {

    }

    private fun uiSetting() {
        toolbarSetting()
        fragmentSetting()

    }

    private fun fragmentSetting() {
        binding.menuBtn.setOnClickListener {
            val menuFragment = fragmentManager.findFragmentByTag("Menu")
            fragmentManager.commit {
                var bundle = Bundle()

                bundle.putInt("test",1)
                menuFragment?.arguments = bundle
                Log.d("Fragment Test","Menu ${fragmentManager.fragments}")
                if (menuFragment != null) {
                    replace(R.id.fl_menu, menuFragment)
                }
            }
        }
        binding.drinkBtn.setOnClickListener {
            val drinkFragment = fragmentManager.findFragmentByTag("Drink")
            fragmentManager.commit {
                var bundle = Bundle()

                bundle.putInt("test",2)
                drinkFragment?.arguments = bundle
                Log.d("Fragment Test","drink ${fragmentManager.fragments}")
                if (drinkFragment != null) {
                    replace(R.id.fl_menu, drinkFragment)
                }
            }
        }
    }

    private fun toolbarSetting() {
        binding.menuToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.menuToolBar.toolBarTitle.text = "메뉴 별 재고 설정"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun changeFragment(fragment: Fragment, tag: String) {

    }
}
