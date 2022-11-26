package com.oneandonly.inventationblock.ui.dialog

import android.app.Dialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.DialogSafeAmountBinding
import java.text.DecimalFormat

class SafeAmountDialog (private val context: AppCompatActivity) {

    private lateinit var binding: DialogSafeAmountBinding
    private val dialog = Dialog(context, R.style.DialogTheme)

    fun show(am: Int, un: String) {
        var amount = am
        //var unit = un
        val unitGap = when (un) {
            "g" -> 100
            "kg" -> 10
            "L" -> 1
            else -> 10
        }
        val df = DecimalFormat("#,###")
        var pointNumStr = df.format(amount)

        binding = DialogSafeAmountBinding.inflate(context.layoutInflater)

        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        binding.dialogAmount.setText(df.format(amount))
        binding.dialogAmount.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //if (!TextUtils.isEmpty(s.toString()) && s.toString() != )
            }

            override fun afterTextChanged(p0: Editable?) {
                //TODO("Not yet implemented")
            }

        })

        binding.dialogMinusBtn.setOnClickListener {
            amount = Integer.parseInt(binding.dialogAmount.text.toString().replace(",","")) - unitGap

            binding.dialogAmount.setText(df.format(amount).toString())
            binding.dialogAmount.setSelection(binding.dialogAmount.length())
        }

        binding.dialogPlusBtn.setOnClickListener {
            amount = Integer.parseInt(binding.dialogAmount.text.toString().replace(",","")) + unitGap

            binding.dialogAmount.setText(df.format(amount).toString())
            binding.dialogAmount.setSelection(binding.dialogAmount.length())
        }

        binding.dialogCheckBtn.setOnClickListener {
            amount = if (!TextUtils.isEmpty(binding.dialogAmount.text.toString())) {
                        Integer.parseInt(binding.dialogAmount.text.toString().replace(",",""))
                    } else {
                        0
                    }
            onClick.onClick(amount)
            dialog.dismiss()
        }

        dialog.setOnCancelListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    interface CheckBtnClickListener {
        fun onClick(amount: Int)
    }

    private lateinit var onClick: CheckBtnClickListener

    fun onClickCheckBtn(listener: CheckBtnClickListener) {
        onClick = listener
    }
}