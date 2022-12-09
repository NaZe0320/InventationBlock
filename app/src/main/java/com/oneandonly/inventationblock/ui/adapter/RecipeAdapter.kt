package com.oneandonly.inventationblock.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ItemRecipeBinding
import com.oneandonly.inventationblock.datasource.model.data.Recipe

class RecipeAdapter(private val recipeList: ArrayList<Recipe>): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

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
        if (selectedPosition == position) {
            holder.binding.itemBackground.background =
                ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.bg_primary_stroke_tv)
            holder.binding.sticker.visibility = View.INVISIBLE
        } else {
            holder.binding.itemBackground.background =
                ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.bg_white_tv)
            holder.binding.sticker.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener { setSelection(position) }

        holder.binding.sticker.setOnClickListener { setSelection(position) }
        Log.d("Selection Test","${holder.binding.name.onFocusChangeListener}")
    }

    private fun setSelection(position: Int) {
        Log.d("Selection Test", "$position")
        if (selectedPosition != position) {
            selectedPosition = position
            notifyDataSetChanged()
        }

    }
}

