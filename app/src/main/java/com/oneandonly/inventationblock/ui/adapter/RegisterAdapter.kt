package com.oneandonly.inventationblock.ui.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.databinding.ItemRegisterBinding
import com.oneandonly.inventationblock.datasource.model.data.RegisterModel


class RegisterAdapter(private val registerList: ArrayList<RegisterModel>): RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>() {

    inner class RegisterViewHolder(private val binding: ItemRegisterBinding): RecyclerView.ViewHolder(binding.root) {
        val item = binding.etItem
        fun bind(register: RegisterModel) {
            binding.register = register
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        return RegisterViewHolder(ItemRegisterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RegisterViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(registerList[position])

        holder.item.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                registerList[position].content = p0.toString()
                //TODO(현재 강제로 입력에 넣어서 해결
                //  추후 더 좋은 방법을 알게 되면 변경할 것)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    override fun getItemCount(): Int {
        return registerList.size
    }
}