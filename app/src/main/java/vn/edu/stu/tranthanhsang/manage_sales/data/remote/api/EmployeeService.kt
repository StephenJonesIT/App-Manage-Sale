package vn.edu.stu.tranthanhsang.manage_sales.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.CreateEmployeeRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.EmployeeResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.UpdateEmployeeRequest

interface EmployeeService {

    @GET("employee")
    suspend fun fetchEmployees(@Query("page") page:Int, @Query("limit") limit:Int):Response<EmployeeResponse>

    @POST("employee")
    suspend fun createEmployee(@Body request: CreateEmployeeRequest): Response<StatusResponse>

    @PATCH("employee/{manv}")
    suspend fun updateEmployee(@Path("manv") manv: String, @Body request: UpdateEmployeeRequest): Response<StatusResponse>

    @DELETE("employee/{manv}")
    suspend fun deleteEmployee(@Path("manv") manv: String): Response<StatusResponse>

}