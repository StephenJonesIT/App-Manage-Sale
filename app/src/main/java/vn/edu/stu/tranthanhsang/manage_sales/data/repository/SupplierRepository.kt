package vn.edu.stu.tranthanhsang.manage_sales.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.ErrorResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.CreateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.SupplierResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.UpdateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit.SupplierServiceClient
import java.io.IOException

class SupplierRepository {
    private val supplierRepository = SupplierServiceClient.supplierService
    fun updateToken(newToken: String) {
        SupplierServiceClient.updateToken(newToken)
    }

    suspend fun fetchSuppliers(page: Int, limit: Int): Result<SupplierResponse> =
        withContext(Dispatchers.IO) {
            val response = supplierRepository.fetchSuppliers(page, limit)
            try {
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorResponse.message
                    } catch (e: Exception) {
                        "Fetch Suppliers error"
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

    suspend fun createSupplier(
        createSupplier: CreateSupplierRequest
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = supplierRepository.createSupplier(createSupplier)
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
                    "Create supplier error"
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

    suspend fun updateSupplier(
        idSupplier: String,
        updateRequest: UpdateSupplierRequest
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = supplierRepository.updateSupplier(idSupplier, updateRequest)
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
                    "Update supplier error"
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

    suspend fun deleteSupplier(
        idSupplier: String
    ): Result<StatusResponse> = withContext(Dispatchers.IO) {
        val response = supplierRepository.deleteSupplier(idSupplier)
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
                    "Delete supplier error"
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