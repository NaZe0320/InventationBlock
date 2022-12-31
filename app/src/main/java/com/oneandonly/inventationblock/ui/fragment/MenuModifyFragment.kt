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
import com.oneandonly.inventationblock.datasource.model.data.Menu
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.ui.adapter.RecipeAdapter
import com.oneandonly.inventationblock.viewmodel.RecipeViewModel
import com.oneandonly.inventationblock.viewmodel.factory.RecipeFactory

class MenuModifyFragment: ContainerFragment(){

    private lateinit var binding: FragmentMenuModifyBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: ArrayList<Recipe> = ArrayList()

    private lateinit var recipeViewModel: RecipeViewModel

    private var rid: Int = 0
    private var name: String =""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_modify, container, false)
        val view = binding.root

        setViewModel()
        setAdapter()//임시

        arguments?.let {
            rid = it.getInt("rid")
            name = it.getString("name").toString()
            uiSetting()
            Log.d("MenuAddFragment", "${it.getInt("rid")} /${it.getInt("count")}/ ${it.getString("name")}")
        }

        recipeViewModel.getRecipeInfo(rid)

        binding.enrollBtn.setOnClickListener {
            setRecipe()
            recipeViewModel.enroll.value = State.Loading
            binding.enrollBtn.isEnabled = false
            recipeViewModel.enroll.observe(requireActivity()) {
                when (it) {
                    State.Success -> {
                        Log.d("Recipe Enroll","Success")
                        activity?.makeToast("$name 등록에 성공했습니다")
                        if (activity != null) {
                            parentFragmentManager.beginTransaction().replace(R.id.fl_menu_menu,MenuFragment(),"Menu").commit()
                        }
                    }
                    State.Fail -> {
                        Log.d("Recipe Enroll","Fail")
                        activity?.makeToast("등록에 실패했습니다")
                        binding.enrollBtn.isEnabled = true
                    }
                    State.Loading -> {
                        Log.d("Recipe Enroll","loading")
                    }
                    State.Null -> {

                    }
                }
            }
        }

        binding.listAddBtn.setOnClickListener {
            recipeList.add(Recipe())
            recipeAdapter.notifyItemInserted(recipeList.size - 1)
        }

        binding.editMinimum.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.editMinimum.text.toString() == "0") {
                    binding.editMinimum.setText("")
                }
            } else {
                if (binding.editMinimum.text.isEmpty()) {
                    binding.editMinimum.setText("0")
                }
            }
        }

        return view
    }

    private fun uiSetting() {
        observer()
    }

    private fun observer() {
        val recipeObserver: Observer<ArrayList<RecipeElement>> = Observer {
            recipeList.clear()
            recipeViewModel.recipeList.value?.forEachIndexed { index, recipe ->
                recipeList.add(Recipe(recipe.name.toString(),recipe.amount.toString(),recipe.unit.toString(),recipe.sid))
            }
            Log.d("Recipe","Recipe Observer $recipeList")
            recipeAdapter = RecipeAdapter(recipeList, requireContext())
            binding.recipeList.adapter = recipeAdapter
        }

        recipeViewModel.recipeList.observe(requireActivity(), recipeObserver)

        val menuObserver: Observer<Menu> = Observer {
            binding.editMinimum.setText("${recipeViewModel.menu.value?.count}")
            Log.d("Menu","${recipeViewModel.menu.value?.count}")
        }
        recipeViewModel.menu.observe(requireActivity(), menuObserver)
    }

    private fun setViewModel() {
        val recipeRepo = RecipeRepository()

        val recipeViewModelFactory = RecipeFactory(recipeRepo)
        recipeViewModel = ViewModelProvider(this@MenuModifyFragment, recipeViewModelFactory)[RecipeViewModel::class.java]
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

    private fun setRecipe() {
        val element: ArrayList<RecipeElement> = ArrayList()
        recipeList.forEachIndexed { index, recipe ->
            if (!(recipe.amount.isEmpty() && recipe.name.isEmpty() && recipe.unit.isEmpty())) {
                val stockAmount: Int = if (recipe.amount.isEmpty()) {
                    0
                } else {
                    recipe.amount.toInt()
                }
                element.add(RecipeElement(sid = null, name = recipe.name,
                    amount = stockAmount, unit = recipe.unit,
                    amountTotal = null, date = null,
                    safeStandard = null, pinned = null
                ))
            }
            Log.d("<RESULT>","$element")
            //TODO(빈칸이 있으면 아예 처리를 안함)
        }
        val min = if (binding.editMinimum.text.isEmpty()) 0 else binding.editMinimum.text.toString().toInt()

        recipeViewModel.setRecipeList(rid, name, min, element )
    }
}