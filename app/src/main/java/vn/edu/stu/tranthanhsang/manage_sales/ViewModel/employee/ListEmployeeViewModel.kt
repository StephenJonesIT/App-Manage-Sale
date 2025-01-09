package vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.Employee
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.EmployeeResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.SupplierResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository

class ListEmployeeViewModel(
    private val employeeRepository: EmployeeRepository
) : ViewModel(){
    private val _employees = MutableLiveData<Result<EmployeeResponse>>()
    val employees: LiveData<Result<EmployeeResponse>> get() = _employees

    fun fetchEmployeesViewModel(page: Int, limit: Int) {
        viewModelScope.launch {
            val results = employeeRepository.fetchEmployees(page,limit)
            _employees.value = results
        }
    }
    fun setTokenViewModel(newToken: String){
        employeeRepository.updateToken(newToken)
    }
}