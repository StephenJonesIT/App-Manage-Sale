package vn.edu.stu.tranthanhsang.manage_sales.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.ErrorResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.LoginRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.LoginResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit.RetrofitClient
import java.io.IOException

class AuthRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> =
        withContext(Dispatchers.IO) {
            val response = apiService.login(loginRequest)
            try {
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.message
                    } catch (e: Exception) {
                        "Login error"
                    }
                    Result.failure(Exception(errorMessage))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Network connection error"))
            } catch (e: HttpException) {
                Result.failure(Exception("Server error: ${e.message()}"))
            } catch (e: Exception) {
                Result.failure(Exception("Unknown error"))
            }
        }
}