package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding
import com.oneandonly.inventationblock.databinding.FragmentMenuModifyBinding
import com.oneandonly.inventationblock.ui.adapter.MenuAddAdapter

class MenuModifyFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuModifyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_modify, container, false)
        val view = binding.root

        arguments?.let {
            Log.d("MenuAddFragment", "1 ${it.getString("name")}")
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Log.d("MenuAddFragment", "2 ${it.getString("name")}")
        }
    }
}