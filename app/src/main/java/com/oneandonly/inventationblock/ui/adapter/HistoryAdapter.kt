package com.oneandonly.inventationblock.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemHistoryBinding
import com.oneandonly.inventationblock.datasource.model.data.History

class HistoryAdapter(private val items: LiveData<ArrayList<History>>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.history = history
            binding.historyAmount.isSelected = history.pm
            binding.historyContent.isSelected = history.pm
            Log.d("History","${history.pm}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        items.value?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return items.value?.size?:0
    }
}