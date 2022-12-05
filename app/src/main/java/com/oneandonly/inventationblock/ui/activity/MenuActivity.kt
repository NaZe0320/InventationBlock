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

        changeFragment(MenuAddFragment(),"Menu")

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
            /*fragmentManager.commit {
                Log.d("Fragment Test","Menu click ${fragmentManager.fragments}")
                if (fragmentManager.findFragmentByTag("Menu") != null) {
                    val menuFragment = fragmentManager.findFragmentByTag("Menu")
                    val bundle = Bundle()
                    Log.d("Fragment Test","Menu not null")
                    bundle.putString("test","menu")
                    menuFragment?.arguments = bundle

                    replace(R.id.fl_menu, menuFragment!!)
                } else {
                    Log.d("Fragment Test","Menu null")
                    add(R.id.fl_menu, MenuAddFragment(), "Menu")
                    addToBackStack(null)
                }
            }*/
            for (fragment: Fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible && fragment.tag != "Menu") {
                    changeFragment(MenuAddFragment(),"Menu")
                    Log.d("Fragment Test","Menu Open")
                }
            }
        }
        binding.drinkBtn.setOnClickListener {
            /*fragmentManager.commit {
                Log.d("Fragment Test","Drink Click ${fragmentManager.fragments}")
                if (fragmentManager.findFragmentByTag("Drink") != null) {
                    val menuFragment = fragmentManager.findFragmentByTag("Drink")
                    val bundle = Bundle()
                    Log.d("Fragment Test","Drink not null")
                    bundle.putString("test","drink")
                    menuFragment?.arguments = bundle

                    replace(R.id.fl_menu, menuFragment!!)
                } else {
                    Log.d("Fragment Test","drink null")
                    add(R.id.fl_menu, MenuAddFragment(), "Drink")
                    addToBackStack(null)
                }
            }*/
            for (fragment: Fragment in supportFragmentManager.fragments) {
                if (fragment.isVisible && fragment.tag != "Drink") {
                    changeFragment(MenuAddFragment(),"Drink")
                    Log.d("Fragment Test","Drink Open")
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
        val bundle = Bundle()
        bundle.putString("type",tag)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_menu, fragment, tag)
        transaction.commit()
    }
}
