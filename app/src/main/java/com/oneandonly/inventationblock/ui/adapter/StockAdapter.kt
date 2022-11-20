package com.oneandonly.inventationblock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemStockBinding
import com.oneandonly.inventationblock.datasource.model.data.Stock

class StockAdapter(private val items: ArrayList<Stock>):RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

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
        holder.bind(items[position])

        holder.btnPin.setOnClickListener {
            holder.btnPin.isSelected = !holder.btnPin.isSelected
            //TODO 데이터 변경 요청
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}