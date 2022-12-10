package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.ui.adapter.RecipeAdapter
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel

class MenuAddFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuAddBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList: ArrayList<Recipe> = ArrayList()

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_add, container, false)
        val view = binding.root

        setViewModel()

        arguments?.let {
            Log.d("MenuAddFragment", "1 ${it.getString("name")}")
            binding.editMenuName.setText(it.getString("name").toString())
        }
        setAdapter()

        binding.enrollBtn.setOnClickListener {
            setRecipe()
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



    private fun setRecipe() {
        Log.d("<RESULT>", "?")
        val element: ArrayList<RecipeElement> = ArrayList()
        recipeList.forEachIndexed { index, recipe ->

            element.add(RecipeElement(recipe.stockName, recipe.stockAmount, recipe.stockUnit))

            Log.d("<RESULT>","$element")
        }


        //recipeViewModel.setRecipeList(binding.editMenuName.text.toString(), binding.editMinimum.text.toString(), element )
    }




    private fun setAdapter() {
        recipeAdapter = RecipeAdapter(recipeList, requireContext())
        binding.recipeList.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
            adapter = recipeAdapter
        }
    }
}