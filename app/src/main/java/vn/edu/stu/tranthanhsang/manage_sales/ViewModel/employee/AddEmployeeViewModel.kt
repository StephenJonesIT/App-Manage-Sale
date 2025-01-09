package vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.CreateEmployeeRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class AddEmployeeViewModel (
    private val employeeRepository: EmployeeRepository
):ViewModel(){
    private val _isStatusSave = MutableLiveData<Boolean>()
    val isStatusSave: LiveData<Boolean> get() = _isStatusSave

    private val _isStatusCreate = MutableLiveData<Result<StatusResponse>>()
    val isStatusCreate: LiveData<Result<StatusResponse>> get() = _isStatusCreate

    private val _idSupplier = MutableLiveData<String>()
    private val _nameSupplier = MutableLiveData<String>()
    private val _telephone = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()

    val idEmployee: LiveData<String> get() = _idSupplier
    val nameEmployee: LiveData<String> get() = _nameSupplier
    val telephone: LiveData<String> get() = _telephone
    val address: LiveData<String> get() = _address

    fun setIdEmployee(id: String) {
        _idSupplier.value = id
    }
    fun setNameEmployee(name: String) {
        _nameSupplier.value = name
    }
    fun setTelephone(telephone: String) {
        _telephone.value = telephone
    }
    fun setAddress(address: String) {
        _address.value = address
    }

    fun createEmployee() {
        val idSupplier = _idSupplier.value
        val nameSupplier = _nameSupplier.value
        val (Ho, Ten) = StringUtils.splitFullName(nameSupplier.toString())
        val telephone = _telephone.value
        val address = _address.value
        viewModelScope.launch {
            val result = employeeRepository.createEmployee(
                CreateEmployeeRequest(
                    idSupplier.toString(),
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString()
                )
            )
            _isStatusCreate.value = result
        }
    }
    fun setToken(newToken: String){
        employeeRepository.updateToken(newToken)
    }
    fun onclickSave(){
        _isStatusSave.value = true
    }
}