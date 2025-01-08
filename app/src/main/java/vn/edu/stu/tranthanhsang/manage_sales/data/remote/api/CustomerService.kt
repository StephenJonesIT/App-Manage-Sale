package vn.edu.stu.tranthanhsang.manage_sales.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.CustomerResponse

interface CustomerService {
    @GET("customer")
    suspend fun fetchCustomers(@Query("loaikh") LoaiKH:String, @Query("page") page:Int, @Query("limit") limit:Int) : Response<CustomerResponse>
}