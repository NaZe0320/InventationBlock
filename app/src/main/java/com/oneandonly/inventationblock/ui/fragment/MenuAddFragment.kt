package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuBinding
import com.oneandonly.inventationblock.datasource.model.data.Menu

class MenuAddFragment:ContainerFragment() {

    private lateinit var binding: FragmentMenuBinding

    private var menuList: ArrayList<Menu> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        val view = binding.root

        setRecyclerView()



        return view
    }

    override fun onResume() {
        val test = arguments?.getInt("test")
        Log.d("Fragment Test", "Resume $test")
        binding.test.text = test.toString()
        super.onResume()
    }

    private fun setRecyclerView() {

    }

}