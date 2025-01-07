package vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.api.AuthService

object AuthServiceClient{
    private const val BASE_URL = "http://10.0.2.2:1000/shop/account/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}
