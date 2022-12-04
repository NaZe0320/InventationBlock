package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuBinding
import com.oneandonly.inventationblock.datasource.model.data.Menu
import com.oneandonly.inventationblock.ui.adapter.MenuAddAdapter

class MenuAddFragment:ContainerFragment() {

    private lateinit var binding: FragmentMenuBinding

    private var list: MutableLiveData<ArrayList<Menu>> = MutableLiveData()
    private var menuList: ArrayList<Menu> = ArrayList()
    private var drinkList: ArrayList<Menu> = ArrayList()

    private lateinit var menuAdapter: MenuAddAdapter

    private var test: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        val view = binding.root
        //Log.d("Fragment Test","onCreateView")
        setRecyclerView()

        return view
    }

    override fun onResume() {
        test = arguments?.getString("test").toString()
        //Log.d("Fragment Test", "Resume $test")
        binding.test.text = test
        super.onResume()
    }

    private fun setRecyclerView() {
        setList() //TEST 코드
        menuAdapter = MenuAddAdapter(list)

        binding.menuList.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
        binding.menuList.adapter = menuAdapter
    }

    private fun setList() {
        when (test) {
            "menu" -> {
                menuList.clear()
                menuList.add(Menu(1,"돼지고기 김치찌개",1,null))
                menuList.add(Menu(2,"돼지고기 김치찌",1,null))
                menuList.add(Menu(3,"돼지고기 김치",1,null))
                menuList.add(Menu(4,"돼지고기 김",1,null))
                menuList.add(Menu(5,"돼지고기 ",1,null))
            }
            "drink" -> {
                menuList.clear()
                menuList.add(Menu(1,"콜라",1,null))
                menuList.add(Menu(2,"사이다",2,null))
                menuList.add(Menu(3,"환타",3,null))
            }
        }
        //Log.d("Fragment Test","List : ${list.value}")
        list.value = menuList
    }

}