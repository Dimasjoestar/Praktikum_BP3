package com.pab.aplikasibersihin.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    fun formatIdr(amount: Double): String {
        val localeId = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localeId)
        format.maximumFractionDigits = 0
        return format.format(amount).replace("Rp", "Rp ")
    }
}
