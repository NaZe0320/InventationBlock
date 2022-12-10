package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.FragmentMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.ui.activity.MenuActivity
import com.oneandonly.inventationblock.ui.adapter.RecipeAdapter
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel

class MenuAddFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuAddBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: ArrayList<Recipe> = ArrayList()

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
            recipeViewModel.ing.value = State.Loading
            binding.enrollBtn.isEnabled = false
            recipeViewModel.ing.observe(requireActivity()) {
                when (it) {
                    State.Success -> {
                        Log.d("Recipe Enroll","Success")
                        requireActivity().makeToast("${binding.editMenuName.text}등록에 성공했습니다")
                        parentFragmentManager.beginTransaction().replace(R.id.fl_menu_menu,MenuFragment(),"Menu").commit()
                    }
                    State.Fail -> {
                        Log.d("Recipe Enroll",".Fail")
                        requireActivity().makeToast("등록에 실패했습니다")
                        binding.enrollBtn.isEnabled = true
                    }
                    State.Loading -> {
                        Log.d("Recipe Enroll","Loading")
                    }
                }
            }
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
        val element: ArrayList<RecipeElement> = ArrayList()
        recipeList.forEachIndexed { index, recipe ->
            if (!(recipe.stockAmount.isEmpty() && recipe.stockName.isEmpty() && recipe.stockUnit.isEmpty())) {
                val stockAmount: Int = if (recipe.stockAmount.isEmpty()) {
                    0
                } else {
                    recipe.stockAmount.toInt()
                }
                element.add(RecipeElement(recipe.stockName, stockAmount, recipe.stockUnit))
            }
            Log.d("<RESULT>","$element")
            //TODO(빈칸이 있으면 아예 처리를 안함)
        }
        recipeViewModel.setRecipeList(binding.editMenuName.text.toString(), binding.editMinimum.text.toString().toInt(), element )
    }




    private fun setAdapter() {
        recipeAdapter = RecipeAdapter(recipeList = recipeList, context = requireContext())
        binding.recipeList.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
            adapter = recipeAdapter
        }
    }
}