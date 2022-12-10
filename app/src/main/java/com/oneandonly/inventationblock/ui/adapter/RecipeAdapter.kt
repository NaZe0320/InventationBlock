package com.oneandonly.inventationblock.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ItemRecipeBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.viewmodel.StockViewModel.Companion.searchStock

class RecipeAdapter(private val recipeList: ArrayList<Recipe>, private val context: Context): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipe = recipe
        }

        /*
        companion object Factory {
            fun create(parent: ViewGroup): ViewHolder {
                *//*
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_recipe_selected, parent, false)
                *//*
                return RecipeSelectedViewHolder(ItemRecipeSelectedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
        */

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


    companion object {
        var selectedPosition = -1
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
/*        if (selectedPosition == position) {
            holder.binding.itemBackground.background =
                ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.bg_primary_stroke_tv)
        } else {
            holder.binding.itemBackground.background =
                ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.bg_white_tv)
        }*/

/*        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
            builder.setTitle("삭제하시겠습니까?")
                .setMessage("삭제하려면 확인을 눌러주세요")
                .setPositiveButton("확인",
                DialogInterface.OnClickListener { _, i ->
                    Log.d("Recipe Test", "position $position")
                    recipeList.removeAt(position)
                    notifyItemRemoved(position)
                })
                .setNegativeButton("취소", null)

            builder.show()
            return@setOnLongClickListener(true)
        }*/

        holder.binding.name.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_NEXT) {
                holder.binding.amount.requestFocus()
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }

        holder.binding.unit.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                holder.binding.unit.clearFocus()
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }

        holder.binding.run {
            name.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    for (i in searchStock) {
                        if (name.text.toString() == i.name) {
                            unit.setText(i.unit)
                            unit.isFocusable = false
                        }
                    }
                }
            }
        }

        val adapter = SearchDropDownAdapter(context, R.layout.item_dropdown2, searchStock)
        holder.binding.run {
            name.setAdapter(adapter)
            name.setOnItemClickListener { adapterView, view, i, l ->
                val selected = adapterView.adapter.getItem(i) as Search
                name.setText(selected.name)
                name.clearFocus()
                amount.requestFocus()
            }
            name.threshold = 1
            name.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    holder.binding.name.clearFocus()
                    holder.binding.amount.requestFocus()
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    interface OnClick {
        fun onClick()
    }
}

