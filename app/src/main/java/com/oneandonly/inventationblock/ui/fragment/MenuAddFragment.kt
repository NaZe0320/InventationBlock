package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.ui.adapter.RecipeAdapter

class MenuAddFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuAddBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList: ArrayList<Recipe> = ArrayList()

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
        setAdapter()

        binding.enrollBtn.setOnClickListener {

        }

        binding.listAddBtn.setOnClickListener {
            recipeList.add(Recipe())
            recipeAdapter.notifyItemInserted(recipeList.size - 1)
        }

        return view
    }

    private fun setAdapter() {
        recipeAdapter = RecipeAdapter(recipeList, requireContext())
        binding.recipeList.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
            adapter = recipeAdapter
        }
    }
}