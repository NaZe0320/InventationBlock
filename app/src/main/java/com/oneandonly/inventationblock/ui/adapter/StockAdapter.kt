package com.oneandonly.inventationblock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemStockBinding
import com.oneandonly.inventationblock.datasource.model.data.Stock

class StockAdapter(private val items: LiveData<ArrayList<Stock>>):RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    var stockItems = ArrayList<Stock>()

    inner class StockViewHolder(private val binding: ItemStockBinding):RecyclerView.ViewHolder(binding.root) {
        val btnPin = binding.listFixedBtn

        fun bind(stock: Stock) {
            binding.stock = stock
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(ItemStockBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        items.value?.get(position)?.let {
            holder.bind(it)
        }
        holder.btnPin.setOnClickListener {
            holder.btnPin.isSelected = !holder.btnPin.isSelected
        }
    }

    override fun getItemCount(): Int {
        return items.value?.size!!
    }

    fun setData(newStockItems: ArrayList<Stock>) {
        val diffCallback = DiffCallback(stockItems, newStockItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        stockItems.clear()
        stockItems.addAll(newStockItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class DiffCallback(
        private var oldList: ArrayList<Stock>,
        private var newList: ArrayList<Stock>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

    }
}