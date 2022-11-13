package com.oneandonly.inventationblock.ui.adapter

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ActivityRegisterBinding
import com.oneandonly.inventationblock.databinding.ItemRegisterEditTextBinding
import com.oneandonly.inventationblock.datasource.model.data.RegisterModel

class RegisterAdapter(private val items: ArrayList<RegisterModel>): RecyclerView.Adapter<RegisterAdapter.ViewHolder>() {




    class ViewHolder(binding: ItemRegisterEditTextBinding): RecyclerView.ViewHolder(binding.root) {
        val item = binding.etItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRegisterEditTextBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: RegisterModel = items[position]
        holder.item.hint = model.hint
        holder.item.inputType = model.inputType

        when(model.num) {
            1 -> {
                //TODO(아이디 조건 설정)
            }
            2 -> {
                //TODO(비밀번호 조건 설정)
            }
            3 -> {
                //TODO(비밀번호 확인 조건 설정)
            }
            7 -> {
                holder.item.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
                //사업자 등록 번호 10자리 제한
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}