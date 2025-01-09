package vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.UpdateEmployeeRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.UpdateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class EditEmployeeViewModel(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {
    private val _isStatusEdit = MutableLiveData<Boolean>()
    private val _isConfirmClicked = MutableLiveData<Boolean>()
    private val _isStatusDelete = MutableLiveData<Result<StatusResponse>>()
    private val _isStatusUpdate = MutableLiveData<Result<StatusResponse>>()

    val isStatusEdit: LiveData<Boolean> get() = _isStatusEdit
    val isConfirm: LiveData<Boolean> get() = _isConfirmClicked
    val isStatusDelete: LiveData<Result<StatusResponse>> get() = _isStatusDelete
    val isStatusUpdate: LiveData<Result<StatusResponse>> get() = _isStatusUpdate

    private val _idEmployee = MutableLiveData<String>()
    private val _nameEmployee = MutableLiveData<String>()
    private val _telephone = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()


    val idEmployee: LiveData<String> get() = _idEmployee
    val nameEmployee: LiveData<String> get() = _nameEmployee
    val telephone: LiveData<String> get() = _telephone
    val address: LiveData<String> get() = _address

    fun setIdEmployee(id: String) {
        _idEmployee.value = id
    }
    fun setNameEmployee(name: String) {
        _nameEmployee.value = name
    }
    fun setTelephone(telephone: String) {
        _telephone.value = telephone
    }
    fun setAddress(address: String) {
        _address.value = address
    }

    init {
        _isStatusEdit.value = false
    }
    fun onStatusEditClicked() {
        _isStatusEdit.value = true
    }

    fun onConfirmClicked() {
        _isConfirmClicked.value = true
    }

    fun setToken(newToken: String){
        employeeRepository.updateToken(newToken)
    }
    fun resetStatusEdit(){
        _isStatusEdit.value = false
    }

    fun updateEmployee(){
        val idEmployee = _idEmployee.value
        val nameEmployee = _nameEmployee.value
        val (Ho, Ten) = StringUtils.splitFullName(nameEmployee.toString())
        val telephone = _telephone.value
        val address = _address.value
        viewModelScope.launch {
            val result = employeeRepository.updateEmployee(
                idEmployee.toString(),
                UpdateEmployeeRequest(
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString(),
                )
            )
            _isStatusUpdate.value = result
        }
    }

    fun deleteEmployee(){
        val idEmployee = _idEmployee.value
        viewModelScope.launch {
            val result = employeeRepository.deleteEmployee(idEmployee.toString())
            _isStatusDelete.value = result
        }
    }
}