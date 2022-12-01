package com.oneandonly.inventationblock.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.datasource.model.data.Search
import java.util.*

class SearchDropDownAdapter(
    context: Context,
    @LayoutRes private val layoutResourceId: Int,
    private val dropList: List<Search>):
    ArrayAdapter<Search>(context,layoutResourceId, dropList), Filterable {
        private var list: List<Search> = dropList

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Search {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return list[position].sid.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        when (layoutResourceId) {
            R.layout.item_dropdown -> {
                val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_dropdown,parent,false)
//        val name: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false) as TextView
//        val type: ImageView = convertView as ImageView? ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false) as ImageView

                val name: TextView = view.findViewById(R.id.dropdown_name)
                val type: ImageView = view.findViewById(R.id.dropdown_type)

                val ddList: Search = getItem(position)
                name.text = ddList.name
                if (ddList.type == "stock") {
                    type.setImageResource(R.drawable.ic_stock_drop_down)
                } else {
                    type.setImageResource(R.drawable.ic_main_menu_drop_down)
                }

                return view
            }
            R.layout.item_dropdown2 -> {
                val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_dropdown2,parent,false)

                val name: TextView = view.findViewById(R.id.dropdown_name)

                val ddList: Search = getItem(position)
                name.text = ddList.name

                return view
            }
            else -> {
                val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_dropdown,parent,false)
//        val name: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false) as TextView
//        val type: ImageView = convertView as ImageView? ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false) as ImageView

                val name: TextView = view.findViewById(R.id.dropdown_name)
                val type: ImageView = view.findViewById(R.id.dropdown_type)

                val ddList: Search = getItem(position)
                name.text = ddList.name
                if (ddList.type == "stock") {
                    type.setImageResource(R.drawable.ic_stock_drop_down)
                } else {
                    type.setImageResource(R.drawable.ic_main_menu_drop_down)
                }
                return view
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.ROOT)

                val filterResults = Filter.FilterResults()

                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dropList
                    else
                        dropList.filter {
                            it.name.contains(queryString) || it.type.contains(queryString)
                        }
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence?,filterResults: FilterResults) {
                list = filterResults.values as List<Search>
                notifyDataSetChanged()
            }

        }
    }
}
