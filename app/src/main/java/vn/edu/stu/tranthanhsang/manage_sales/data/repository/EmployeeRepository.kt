package vn.edu.stu.tranthanhsang.manage_sales.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.ErrorResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.CreateEmployeeRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.EmployeeResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.UpdateEmployeeRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit.EmployeeServiceClient
import java.io.IOException

class EmployeeRepository {
    private val employeeRepository = EmployeeServiceClient.employeeService
    fun updateToken(newToken: String) {
        EmployeeServiceClient.updateToken(newToken)
    }

    suspend fun fetchEmployees(
        page: Int,
        limit: Int
    ): Result<EmployeeResponse> = withContext(Dispatchers.IO) {
        val response = employeeRepository.fetchEmployees(page, limit)
        try {
            if (response.isSuccessful) {
                Log.d("RESPONSE", response.body().toString())
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    "Fetch Employees error"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error"))
        } catch (e: IOException) {
            Result.failure(Exception("Network connection error"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error: ${e.message()}"))
        }
    }

    suspend fun createEmployee(
        createEmployee: CreateEmployeeRequest
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = employeeRepository.createEmployee(createEmployee)
        try {
            if (response.isSuccessful) {
                Log.d("RESPONSE", response.body().toString())
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.toString()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    "Create employee error"
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

    suspend fun updateEmployee(
        idEmployee: String,
        updateEmployee: UpdateEmployeeRequest
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = employeeRepository.updateEmployee(idEmployee, updateEmployee)
        try {
            if (response.isSuccessful) {
                Log.d("RESPONSE", response.body().toString())
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.toString()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    "Update employee error"
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

    suspend fun deleteEmployee(
        idEmployee: String
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = employeeRepository.deleteEmployee(idEmployee)
        try {
            if (response.isSuccessful) {
                Log.d("RESPONSE", response.body().toString())
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    "Delete employee error"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network connection error"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error"))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error"))
        }
    }
}
