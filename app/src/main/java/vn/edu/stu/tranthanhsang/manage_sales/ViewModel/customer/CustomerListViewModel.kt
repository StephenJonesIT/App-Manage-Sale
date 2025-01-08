package vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.CustomerResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository

class CustomerListViewModel(
    private val customerRepository:CustomerRepository
) : ViewModel(){

    private val _customers = MutableLiveData<Result<CustomerResponse>>()
    val customers: LiveData<Result<CustomerResponse>> = _customers

    fun fetchCustomersViewModel(filter: String,page: Int, limit: Int) {
        viewModelScope.launch {
            _customers.value = customerRepository.fetchCustomers(filter,page,limit)
        }
    }

    fun setTokenViewModel(newToken: String){
        customerRepository.updateToken(newToken)
    }
}