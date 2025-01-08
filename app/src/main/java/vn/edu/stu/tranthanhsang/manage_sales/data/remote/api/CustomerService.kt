package vn.edu.stu.tranthanhsang.manage_sales.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.CustomerResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.UpdateCustomerRequest

interface CustomerService {
    @GET("customer")
    suspend fun fetchCustomers(@Query("loaikh") LoaiKH:String, @Query("page") page:Int, @Query("limit") limit:Int) : Response<CustomerResponse>

    @PATCH("customer/{makh}")
    suspend fun updateCustomer(@Path("makh") makh:String, @Body request: UpdateCustomerRequest):Response<StatusResponse>
}