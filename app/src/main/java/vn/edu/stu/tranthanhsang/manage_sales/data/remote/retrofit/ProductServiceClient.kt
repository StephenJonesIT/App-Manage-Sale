package vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.api.ProductService

object ProductServiceClient {
    private const val BASE_URL = "http://10.0.2.2:1000/shop/"
    private var _token: String? = null

    var token: String?
        get() = _token
        private set(value) {
            _token = value
        }
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ProductInterceptor())
            .build()
    }
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val productService:ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

    fun updateToken(newToken: String){
        token = newToken
    }
}