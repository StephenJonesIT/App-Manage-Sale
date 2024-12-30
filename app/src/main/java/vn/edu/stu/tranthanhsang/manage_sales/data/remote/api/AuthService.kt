package vn.edu.stu.tranthanhsang.manage_sales.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.LoginRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.LoginResponse

interface AuthService {
    @POST("login")
    suspend fun login(@Body request:LoginRequest) : Response<LoginResponse>
}