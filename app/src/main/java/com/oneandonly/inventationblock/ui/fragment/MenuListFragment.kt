package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuListBinding
import com.oneandonly.inventationblock.datasource.model.data.Menu
import com.oneandonly.inventationblock.ui.activity.MenuActivity
import com.oneandonly.inventationblock.ui.adapter.MenuAddAdapter
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel
import kotlinx.coroutines.*
import java.util.SimpleTimeZone

class MenuListFragment(val type: String):ContainerFragment() {

    private lateinit var binding: FragmentMenuListBinding

    private var list: MutableLiveData<ArrayList<Menu>> = MutableLiveData()
    private var menuList: ArrayList<Menu> = ArrayList()
    private var drinkList: ArrayList<Menu> = ArrayList()

    private lateinit var menuAdapter: MenuAddAdapter

    private lateinit var recipeViewModel: RecipeViewModel

    private var test: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_list, container, false)
        val view = binding.root
        //Log.d("Fragment Test","onCreateView")
        setList()


        binding.menuList.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            test = it.getString("type")
        }

        setViewModel()



    }
    private fun setViewModel() {
        recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }

    override fun onResume() {
        //Log.d("Fragment Test", "Resume $test")
        super.onResume()
    }

    private fun setRecyclerView() {
        menuAdapter = MenuAddAdapter(list, this)
        binding.menuList.adapter = menuAdapter
    }

    private fun setList() {
        Log.d("Fragment Test","setList $type")
        when (type) {
            "Menu" -> {
                recipeViewModel.getRecipeList()
                val menuObserver: Observer<ArrayList<Menu>> = Observer {
                    menuList.clear()
                    recipeViewModel.menuList.value?.forEachIndexed { index, s ->
                        menuList.add(Menu(s.rid,s.name,s.count,s.recipe))
                    }
                    list.value = menuList
                    setRecyclerView()
                }
                recipeViewModel.menuList.observe(requireActivity(),menuObserver)
            }
            "Drink" -> {
                menuList.clear()
                menuList.add(Menu(0,"콜라",null,null))
                menuList.add(Menu(0,"사이다",null,null))
                menuList.add(Menu(0,"환타",null,null))
                list.value = menuList
                setRecyclerView()
            }
        }
        Log.d("Fragment Test","List : ${list.value}")

    }

    override fun onDetach() {
        Log.d("Fragment Test","Detach $test")
        super.onDetach()
    }
}