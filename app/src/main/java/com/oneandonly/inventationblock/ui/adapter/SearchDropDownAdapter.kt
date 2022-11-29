package com.oneandonly.inventationblock.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.datasource.model.data.Search

class SearchDropDownAdapter(context: Context, private val dropList: List<Search>):
    ArrayAdapter<Search>(context,0, dropList), Filterable {

    private var list: List<Search> = dropList

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_dropdown,parent, false)

        val name: TextView = view.findViewById(R.id.dropdown_name)
        val type: ImageView = view.findViewById(R.id.dropdown_type)

        val ddList : Search = getItem(position)

        name.text = ddList.name

        if(ddList.type == "stock") {
            type.setImageResource(R.drawable.ic_stock_drop_down)
        } else {
            type.setImageResource(R.drawable.ic_main_menu_drop_down)
        }

        return view
    }

    override fun getItem(position: Int): Search{
        return list[position]
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(cs: CharSequence?): FilterResults {
                val queryString = cs.toString()

                val filterResult = FilterResults()
                filterResult.values = if (queryString == null || queryString.isEmpty())
                    list
                else
                    list.filter {
                        it.name.contains(queryString)
                    }
                return filterResult
            }

            override fun publishResults(cs: CharSequence?, filterResults: FilterResults?) {
                list = filterResults?.values as List<Search>
                notifyDataSetChanged()
            }

        }
    }
}
