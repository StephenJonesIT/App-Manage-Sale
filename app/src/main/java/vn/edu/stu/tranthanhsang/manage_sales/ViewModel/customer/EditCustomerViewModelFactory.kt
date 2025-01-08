package vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import java.lang.IllegalArgumentException

class EditCustomerViewModelFactory(
    private val customerRepository: CustomerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditCustomerViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EditCustomerViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}