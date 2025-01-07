package vn.edu.stu.tranthanhsang.manage_sales.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.ErrorResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.CreateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.ProductResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.UpdateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit.ProductServiceClient
import java.io.IOException

class ProductRepository {

    private val productService = ProductServiceClient.productService

    suspend fun fetchProducts(
        page: Int,
        limit: Int
    ): Result<ProductResponse> =
        withContext(Dispatchers.IO) {
            val response = productService.fetchProducts(page, limit)
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
                        "Fetch Products error"
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

    fun setToken(newToken: String) {
        ProductServiceClient.updateToken(newToken)
    }

    suspend fun deleteProduct(
        idProduct: String
    ): Result<StatusResponse> =
        withContext(Dispatchers.IO) {
            val response = productService.deleteProduct(idProduct)
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
                        "Deleted error"
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

    suspend fun createProduct(
        createProduct: CreateProductRequest
    ): Result<StatusResponse> =
        withContext(Dispatchers.IO) {
            val response = productService.createProduct(createProduct)
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
                        "Created error"
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

    suspend fun updateProduct(
        idProduct: String,
        updateRequest: UpdateProductRequest
    ): Result<StatusResponse> =
        withContext(Dispatchers.IO) {
            val response = productService.updateProduct(idProduct, updateRequest)
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
                        "Updated error"
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