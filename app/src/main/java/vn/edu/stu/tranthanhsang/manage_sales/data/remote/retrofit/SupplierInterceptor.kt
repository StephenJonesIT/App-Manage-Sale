package vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class SupplierInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SupplierServiceClient.token
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token.toString())
            .build()
        return chain.proceed(newRequest)
    }
}