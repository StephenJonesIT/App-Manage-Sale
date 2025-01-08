package vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.UpdateCustomerRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class EditCustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _idCustomer = MutableLiveData<String>()
    private val _nameCustomer = MutableLiveData<String>()
    private val _telephone = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _customerType = MutableLiveData<String>()

    private val _isStatusEdit = MutableLiveData<Boolean>()
    private val _isCancelClicked = MutableLiveData<Boolean>()
    private val _isConfirmClicked = MutableLiveData<Boolean>()
    private val _isStatusUpdate = MutableLiveData<Result<StatusResponse>>()


    val isStatusEdit: LiveData<Boolean> get() = _isStatusEdit
    val isCancel: LiveData<Boolean> get() = _isCancelClicked
    val isConfirm: LiveData<Boolean> get() = _isConfirmClicked
    val isStatusUpdate: LiveData<Result<StatusResponse>> get() = _isStatusUpdate

    val isCustomerType: LiveData<String> get() = _customerType
    val idCustomer: LiveData<String> get() = _idCustomer
    val nameCustomer: LiveData<String> get() = _nameCustomer
    val telephone: LiveData<String> get() = _telephone
    val address: LiveData<String> get() = _address

    init {
        _isStatusEdit.value = false
    }
    fun onStatusEditClicked() {
        _isStatusEdit.value = true
    }

    fun onCancelClicked() {
        _isCancelClicked.value = true
    }
    fun resetStatusEdit(){
        _isStatusEdit.value = false
    }
    fun onConfirmClicked() {
        _isConfirmClicked.value = true
    }
    fun setCustomerType(type: String) {
        _customerType.value = type
    }
    fun setIdCustomer(id: String) {
        _idCustomer.value = id
    }
    fun setNameCustomer(name: String){
        _nameCustomer.value = name
    }
    fun setTelephone(telephone: String){
        _telephone.value = telephone
    }
    fun setAddress(address: String){
        _address.value = address
    }

    fun updateCustomer(){
        val idCustomer = _idCustomer.value
        val nameCustomer = _nameCustomer.value
        val (Ho, Ten) = StringUtils.splitFullName(nameCustomer.toString())
        val telephone = _telephone.value
        val address = _address.value
        val customerType = _customerType.value
        viewModelScope.launch {
            val result = customerRepository.updateCustomer(
                idCustomer.toString(),
                UpdateCustomerRequest(
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString(),
                    customerType.toString()
                )
            )
            _isStatusUpdate.value = result
        }
    }

    fun setTokenViewModel(newToken: String){
        customerRepository.updateToken(newToken)
    }
}