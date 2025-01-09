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
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.CreateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.SupplierResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.UpdateSupplierRequest

interface SupplierService {
    @GET("suppliers")
    suspend fun fetchSuppliers(@Query("page") page: Int,@Query("limit") limit: Int): Response<SupplierResponse>

    @POST("suppliers")
    suspend fun createSupplier(@Body request: CreateSupplierRequest): Response<StatusResponse>

    @PATCH("suppliers/{mancc}")
    suspend fun updateSupplier(@Path("mancc") mancc: String, @Body request: UpdateSupplierRequest): Response<StatusResponse>

    @DELETE("suppliers/{mancc}")
    suspend fun deleteSupplier(@Path("mancc") mancc: String): Response<StatusResponse>

}