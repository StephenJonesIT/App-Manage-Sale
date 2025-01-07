package vn.edu.stu.tranthanhsang.manage_sales.utils

import android.content.Context

class TokenManage(context: Context) {
    private val myToken: String = "my_token"
    private val saveToken = context.getSharedPreferences(myToken, Context.MODE_PRIVATE)
    fun saveToken(token: String) {
        saveToken.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return saveToken.getString("auth_token", null)
    }
}