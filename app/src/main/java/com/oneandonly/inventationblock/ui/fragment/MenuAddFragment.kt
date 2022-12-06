package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding

class MenuAddFragment: ContainerFragment() {

    private lateinit var binding: FragmentMenuAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_add, container, false)
        val view = binding.root

        return view
    }
}