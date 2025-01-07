package vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ProductServiceClient.token
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token.toString())
            .build()
        Log.d("AuthInterceptor", "Request Headers: ${newRequest.headers}")
        return chain.proceed(newRequest)
    }
}