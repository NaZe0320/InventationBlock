package com.oneandonly.inventationblock.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemMenuAddBinding
import com.oneandonly.inventationblock.databinding.ItemMenuBinding
import com.oneandonly.inventationblock.datasource.model.data.Menu

class MenuAddAdapter(private val items: LiveData<ArrayList<Menu>>):RecyclerView.Adapter<MenuAddAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(private val binding: ItemMenuAddBinding): RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context!!

        fun bind(menu: Menu) {
            binding.menu = menu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder((ItemMenuAddBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        items.value?.get(position)?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            //val itemIntent = Intent(holder.context, ) //TODO(메뉴 레시피 화면 이동)
        }
    }

    override fun getItemCount(): Int {
        return items.value?.size?:0
    }

}