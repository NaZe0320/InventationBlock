package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
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

                val menuFragment = fragmentManager.findFragmentByTag("Menu")
                val bundle = Bundle()

                bundle.putString("test","menu")
                menuFragment?.arguments = bundle
                Log.d("Fragment Test","Menu ${fragmentManager.fragments}")
                if (menuFragment != null) {
                    replace(R.id.fl_menu, menuFragment)
                } else {
                    add(R.id.fl_menu, MenuAddFragment(), "Menu")
                }
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
                val bundle = Bundle()

                bundle.putString("test","menu")
                menuFragment?.arguments = bundle
                Log.d("Fragment Test","Menu ${fragmentManager.fragments}")
                if (menuFragment != null) {
                    replace(R.id.fl_menu, menuFragment)
                } else {
                    add(R.id.fl_menu, MenuAddFragment(), "Menu")
                }
            }
        }
        binding.drinkBtn.setOnClickListener {
            val drinkFragment = fragmentManager.findFragmentByTag("Drink")
            fragmentManager.commit {
                val bundle = Bundle()

                bundle.putString("test","drink")
                drinkFragment?.arguments = bundle
                Log.d("Fragment Test","drink ${fragmentManager.fragments}")
                if (drinkFragment != null) {
                    replace(R.id.fl_menu, drinkFragment)
                } else {
                    add(R.id.fl_menu, MenuAddFragment(), "Drink")
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
