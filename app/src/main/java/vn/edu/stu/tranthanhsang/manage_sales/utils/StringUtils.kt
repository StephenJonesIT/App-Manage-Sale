package vn.edu.stu.tranthanhsang.manage_sales.utils

object StringUtils {

    fun splitFullName(fullName: String): Pair<String, String> {
        val parts = fullName.split(" ")
        return if (parts.size > 1) {
            val lastName = parts.last().capitalizeFirstLetter()
            val firstNameAndMiddleName = parts.subList(0, parts.size - 1)
                .joinToString(" ") { it.capitalizeFirstLetter() }
            Pair (firstNameAndMiddleName, lastName)
        } else {
            Pair("",fullName.capitalizeFirstLetter())
        }
    } // Hàm mở rộng để viết hoa chữ cái đầu tiên của mỗi từ
    fun String.capitalizeFirstLetter(): String {
        return this.replaceFirstChar {
            it.uppercase()
        }
    }
}