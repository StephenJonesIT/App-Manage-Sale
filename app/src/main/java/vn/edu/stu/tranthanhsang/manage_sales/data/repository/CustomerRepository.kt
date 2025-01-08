package vn.edu.stu.tranthanhsang.manage_sales.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.ErrorResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.CustomerResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.UpdateCustomerRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.UpdateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.remote.retrofit.CustomerServiceClient
import java.io.IOException

class CustomerRepository {
    private val customerRepository = CustomerServiceClient.customerService

    suspend fun fetchCustomers(
        typeCustomer: String,
        page: Int,
        limit: Int
    ): Result<CustomerResponse> =
        withContext(Dispatchers.IO){
        val response = customerRepository.fetchCustomers(typeCustomer,page,limit)
        try{
            if(response.isSuccessful){
                Log.d("RESPONSE", response.body().toString())
                Result.success(response.body()!!)
            }else{
                val errorBody = response.errorBody()?.string()
                val errorMessage = try{
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    errorResponse.message
                }catch (e: Exception){
                    "Fetch Customers error"
                }
                Result.failure(Exception(errorMessage))
            }
        }catch (e: IOException){
            Result.failure(Exception("Network connection error"))
        }catch (e: HttpException){
            Result.failure(Exception("Server error: ${e.message()}"))
        }catch (e: Exception){
            Result.failure(Exception("Unknown error"))
        }
    }

    fun updateToken(newToken: String){
        CustomerServiceClient.updateToken(newToken)
    }

    suspend fun updateCustomer(
        idCustomer: String,
        updateRequest: UpdateCustomerRequest
    ): Result<StatusResponse> =
        withContext(Dispatchers.IO) {
            val response = customerRepository.updateCustomer(idCustomer,updateRequest)
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