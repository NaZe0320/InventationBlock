package com.oneandonly.inventationblock.ui.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Menu
import com.oneandonly.inventationblock.ui.activity.MenuActivity
import com.oneandonly.inventationblock.ui.fragment.MenuModifyFragment

class MenuAddAdapter(private val items: LiveData<ArrayList<Menu>>, private val fragment_s: Fragment):RecyclerView.Adapter<MenuAddAdapter.MenuViewHolder>() {

    private var activity: MenuActivity?= null

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
            val fragment: Fragment = MenuModifyFragment()
            val bundle = Bundle()

            bundle.putInt("rid",items.value?.get(position)?.rid?:26)
            bundle.putString("name",items.value?.get(position)?.name)
            Log.d("MenuModifyFragment","${items.value?.get(position)?.name}")
            fragment.arguments = bundle

            activity = fragment_s.activity as MenuActivity?
            activity?.changeFragment(fragment,"MenuModify")
            activity?.changeToolBar(items.value?.get(position)?.name?:"메뉴 별 재고 설정")

        }
    }

    override fun getItemCount(): Int {
        return items.value?.size?:0
    }

}