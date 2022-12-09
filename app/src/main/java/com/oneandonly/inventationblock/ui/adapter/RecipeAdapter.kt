package com.oneandonly.inventationblock.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.*
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
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.makeToast
import com.oneandonly.inventationblock.ui.activity.MainActivity.Companion.searchList

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
        holder.binding.sticker.setOnLongClickListener {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
            builder.setTitle("삭제하시겠습니까?")
                .setMessage("삭제하려면 확인을 눌러주세요")
                .setPositiveButton("확인",
                DialogInterface.OnClickListener { _, i ->
                    Log.d("Recipe Test", "position $position")
                    recipeList.removeAt(position)
                    notifyDataSetChanged()
                })
                .setNegativeButton("취소", null)

            builder.show()
            return@setOnLongClickListener(true)
        }

        holder.binding.sticker.setOnClickListener {
            setSelection(position)
        }

/*        holder.binding.name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (holder.binding.name.text != null && holder.binding.amount.text != null && holder.binding.unit.text != null)
                    setSelection(-1)

            }
        }

        holder.binding.amount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (holder.binding.name.text != null && holder.binding.amount.text != null && holder.binding.unit.text != null)
                    setSelection(-1)
            }
        }*/

        holder.binding.unit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (holder.binding.name.text != null && holder.binding.amount.text != null && holder.binding.unit.text != null)
                    setSelection(-1)
            }
        }

        val adapter = SearchDropDownAdapter(context, R.layout.item_dropdown2, searchList)
        holder.binding.run {
            name.setAdapter(adapter)
            name.threshold = 1
            name.setOnItemClickListener { adapterView, view, i, l ->
                val selected = adapterView.adapter.getItem(i) as Search
                name.setText(selected.name)
                unit.setText(selected.unit)
                unit.isFocusable = false
                amount.requestFocus()
            }
        }


    }

    private fun setSelection(position: Int) {
        Log.d("Selection Test", "$position")
        if (selectedPosition != position) {
            selectedPosition = position
            notifyDataSetChanged()
        }

    }
}

