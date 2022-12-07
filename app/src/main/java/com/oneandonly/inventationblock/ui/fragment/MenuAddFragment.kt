package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding
import com.oneandonly.inventationblock.ui.adapter.MenuAddAdapter

class MenuAddFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_add, container, false)
        val view = binding.root

        arguments?.let {
            Log.d("MenuAddFragment", "1 ${it.getString("name")}")
            binding.editMenuName.setText(it.getString("name").toString())
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