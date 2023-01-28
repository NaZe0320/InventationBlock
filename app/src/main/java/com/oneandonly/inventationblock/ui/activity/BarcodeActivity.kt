package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityBarcodeBinding

class BarcodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@BarcodeActivity, R.layout.activity_main)
        binding.lifecycleOwner = this@BarcodeActivity


    }

}