package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuModifyBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.ui.adapter.RecipeAdapter
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel

class MenuModifyFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuModifyBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: ArrayList<Recipe> = ArrayList()

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_modify, container, false)
        val view = binding.root

        setViewModel()
        setAdapter()//임시
        getList()
        setObserver()

        arguments?.let {
            Log.d("MenuAddFragment", "1 ${it.getString("name")}")
        }


        binding.enrollBtn.setOnClickListener {

        }

        binding.listAddBtn.setOnClickListener {
            recipeList.add(Recipe())
            recipeAdapter.notifyItemInserted(recipeList.size - 1)
        }

        return view
    }

    private fun setViewModel() {
        recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }

    private fun getList() {
        recipeViewModel.getRecipeList()
    }

    private fun setObserver() {
    //    observeRecipeList()
    }

    private fun setAdapter() {
        recipeViewModel.getRecipeList()

        recipeAdapter = RecipeAdapter(recipeList, requireContext())
        binding.recipeList.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL, false)
            adapter = recipeAdapter
        }
    }

/*    private fun observeRecipeList() {
        val recipeObserver: Observer<ArrayList<Recipe>> = Observer {
            recipeAdapter = RecipeAdapter(recipeViewModel.recipeList, requireContext())
            binding.recipeList.apply {
                layoutManager = LinearLayoutManager(requireActivity(),
                    LinearLayoutManager.VERTICAL, false)
                adapter = recipeAdapter
            }
        }
        recipeViewModel.recipeList.observe(requireActivity(), recipeObserver)
    }*/
}