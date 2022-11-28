package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMenuAddBinding
import com.oneandonly.inventationblock.dateToString
import java.util.Calendar

class MenuAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_add)

        val mCurrentTime = Calendar.getInstance()
        val date = dateToString(mCurrentTime.time)

        uiSetting(date)


    }

    private fun uiSetting(date: String) {

        binding.menuToolBar.toolBarTitle.text = "충원 재고 직접 입력하기"
        binding.dateEdit.text = date

        binding.menuToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.dateEdit.setOnClickListener {
            Log.d("MenuAdd","Date : $date")
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}