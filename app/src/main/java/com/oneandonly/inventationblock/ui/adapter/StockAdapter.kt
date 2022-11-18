package com.oneandonly.inventationblock.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemStockBinding
import com.oneandonly.inventationblock.datasource.model.data.Stock

class StockAdapter {

    inner class StockViewHolder(private val binding: ItemStockBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: Stock) {
            binding.stock = stock
        }
    }
    
}