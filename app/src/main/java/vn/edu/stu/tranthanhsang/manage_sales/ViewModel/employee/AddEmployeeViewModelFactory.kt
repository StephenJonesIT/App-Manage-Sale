package vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository

class AddEmployeeViewModelFactory(
    private val employeeRepository: EmployeeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AddEmployeeViewModel::class.java)){
            return AddEmployeeViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}