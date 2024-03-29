package com.oneandonly.inventationblock.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemStockBinding
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.ui.activity.MainActivity
import com.oneandonly.inventationblock.ui.activity.StockActivity
import com.oneandonly.inventationblock.viewmodel.StockViewModel

class StockAdapter(private val items: LiveData<ArrayList<Stock>>, private val stockViewModel: StockViewModel,private val onClick: OnClick):RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(val binding: ItemStockBinding):RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context!!
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
            Log.d("Stock_Adapter","$it")
        }
        holder.btnPin.setOnClickListener {
            holder.btnPin.isSelected = !holder.btnPin.isSelected
            Log.d("onClick","${holder.btnPin.isSelected}")
            stockViewModel.setToggle(items.value?.get(position)?.sid?:0)
            onClick.onClick()
        }
        holder.itemView.setOnClickListener {
            val itemIntent = Intent(holder.context, StockActivity::class.java)
            itemIntent.putExtra("name",items.value?.get(position)?.name)
            itemIntent.putExtra("stockCurrent",items.value?.get(position)?.stockCurrent)
            itemIntent.putExtra("stockSafe",items.value?.get(position)?.stockSafe)
            itemIntent.putExtra("fixed",items.value?.get(position)?.fixed)
            itemIntent.putExtra("unit",items.value?.get(position)?.unit)
            itemIntent.putExtra("sid",items.value?.get(position)?.sid)
            itemIntent.run { holder.context.startActivity(this)}
            Log.d("Stock_Adapter","화면 이동 ${items.value?.get(position)?.name}")
        }
        holder.binding.stockBar.isClickable = false
        holder.binding.stockBar.isEnabled = false

    }

    override fun getItemCount(): Int {
        return items.value?.size?:0
    }
/*
    var stockItems = ArrayList<Stock>()

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
 */

    interface OnClick {
        fun onClick()
    }
}