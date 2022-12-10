package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuBinding
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel

class MenuFragment: ContainerFragment() {

    private lateinit var binding: FragmentMenuBinding

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        val view = binding.root

        Log.d("Fragment Test","?")

        changeFragment(MenuListFragment("Menu"),"Menu")
        binding.menuBtn.isSelected = true
        binding.drinkBtn.isSelected = false
        fragmentSetting()
        menuAddSetting()
        setViewModel()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setViewModel() {
        recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }


    private fun menuAddSetting() {
        binding.menuAddBtn.setOnClickListener {
            Log.d("Fragment Test","menuAdd")
            parentFragmentManager.beginTransaction().replace(R.id.fl_menu_menu, MenuAddFragment(),"MenuAdd").commit()
        }
    }

    private fun fragmentSetting() {
        binding.menuBtn.setOnClickListener {
            Log.d("Fragment Test","Menu Click")
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

            for (fragment: Fragment in childFragmentManager.fragments) {
                if (fragment.isVisible && fragment.tag != "Menu") {
                    changeFragment(MenuListFragment("Menu"),"Menu")
                    Log.d("Fragment Test","Menu Open")
                    binding.menuBtn.isSelected = true
                    binding.drinkBtn.isSelected = false
                }
            }

        }
        binding.drinkBtn.setOnClickListener {
            Log.d("Fragment Test","Drink Click")
            for (fragment: Fragment in childFragmentManager.fragments) {
                if (fragment.isVisible && fragment.tag != "Drink") {
                    changeFragment(MenuListFragment("Drink"),"Drink")
                    Log.d("Fragment Test","Drink Open")
                    binding.menuBtn.isSelected = false
                    binding.drinkBtn.isSelected = true
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment, tag: String) {
        val bundle = Bundle()
        bundle.putString("type",tag)
        fragment.arguments = bundle

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_menu, fragment, tag)
        transaction.commit()
    }


}