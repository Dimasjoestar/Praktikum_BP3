package com.pab.moneytracker.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class NumberTextWatcher(private val editText: EditText) : TextWatcher {

    private var current = ""
    private val decimalFormat: DecimalFormat

    init {
        val symbols = DecimalFormatSymbols(Locale("id", "ID"))
        symbols.groupingSeparator = '.'
        decimalFormat = DecimalFormat("#,###", symbols)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s.toString() != current) {
            editText.removeTextChangedListener(this)

            val cleanString = s.toString().replace(".", "").replace(",", "")

            if (cleanString.isNotEmpty()) {
                try {
                    val parsed = cleanString.toLong()
                    val formatted = decimalFormat.format(parsed)
                    current = formatted
                    editText.setText(formatted)
                    editText.setSelection(formatted.length)
                } catch (e: NumberFormatException) {
                    // Handle error
                }
            } else {
                current = ""
            }

            editText.addTextChangedListener(this)
        }
    }

    companion object {
        fun getCleanNumber(text: String): Double {
            return try {
                text.replace(".", "").replace(",", ".").toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }
    }
}
