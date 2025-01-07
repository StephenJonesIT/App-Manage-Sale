package vn.edu.stu.tranthanhsang.manage_sales.utils

import java.text.NumberFormat
import java.util.Locale

object PriceUtils {
    fun formatNumber(value: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(value)
    }

    fun removeCommas(numberWithCommas: String): String {
        return numberWithCommas.replace(",", "")
    }
}