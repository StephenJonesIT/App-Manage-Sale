package vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository

class ListEmployeeViewModelFactory(
    private val employeeRepository: EmployeeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListEmployeeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListEmployeeViewModel(employeeRepository) as T
        }
       throw IllegalArgumentException("Unknown ViewModel class")
    }
}