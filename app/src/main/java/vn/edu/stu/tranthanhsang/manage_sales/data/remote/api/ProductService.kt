package vn.edu.stu.tranthanhsang.manage_sales.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.CreateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.ProductResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.UpdateProductRequest

interface ProductService {
    @GET("bonsais")
    suspend fun fetchProducts(@Query("page") page:Int, @Query("limit") limit:Int) : Response<ProductResponse>

    @DELETE("bonsais/{mssp}")
    suspend fun deleteProduct(@Path("mssp") masp:String) : Response<StatusResponse>

    @PATCH("bonsais/{mssp}")
    suspend fun updateProduct(@Path("mssp") masp:String, @Body request:UpdateProductRequest):Response<StatusResponse>

    @POST("bonsais")
    suspend fun createProduct(@Body request:CreateProductRequest):Response<StatusResponse>

}