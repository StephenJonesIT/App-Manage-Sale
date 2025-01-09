package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.UpdateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class EditSupplierViewModel(
    private val supplierRepository: SupplierRepository
) : ViewModel() {
    private val _isStatusEdit = MutableLiveData<Boolean>()
    private val _isConfirmClicked = MutableLiveData<Boolean>()
    private val _isStatusDelete = MutableLiveData<Result<StatusResponse>>()
    private val _isStatusUpdate = MutableLiveData<Result<StatusResponse>>()

    val isStatusEdit: LiveData<Boolean> get() = _isStatusEdit
    val isConfirm: LiveData<Boolean> get() = _isConfirmClicked
    val isStatusDelete: LiveData<Result<StatusResponse>> get() = _isStatusDelete
    val isStatusUpdate: LiveData<Result<StatusResponse>> get() = _isStatusUpdate

    private val _idSupplier = MutableLiveData<String>()
    private val _nameSupplier = MutableLiveData<String>()
    private val _telephone = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _supplierType = MutableLiveData<String>()


    val idSupplier: LiveData<String> get() = _idSupplier
    val nameSupplier: LiveData<String> get() = _nameSupplier
    val telephone: LiveData<String> get() = _telephone
    val address: LiveData<String> get() = _address
    val supplierType: LiveData<String> get() = _supplierType

    fun setIdSupplier(id: String) {
        _idSupplier.value = id
    }
    fun setNameSupplier(name: String) {
        _nameSupplier.value = name
    }
    fun setTelephone(telephone: String) {
        _telephone.value = telephone
    }
    fun setAddress(address: String) {
        _address.value = address
    }
    fun setSupplierType(type: String) {
        _supplierType.value = type
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
        supplierRepository.updateToken(newToken)
    }
    fun resetStatusEdit(){
        _isStatusEdit.value = false
    }

    fun updateSupplier(){
        val idSupplier = _idSupplier.value
        val nameSupplier = _nameSupplier.value
        val (Ho, Ten) = StringUtils.splitFullName(nameSupplier.toString())
        val telephone = _telephone.value
        val address = _address.value
        val supplierType = _supplierType.value
        viewModelScope.launch {
            val result = supplierRepository.updateSupplier(
                idSupplier.toString(),
                UpdateSupplierRequest(
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString(),
                    supplierType?.toInt()?:3
                )
            )
            _isStatusUpdate.value = result
        }
    }

    fun deleteSupplier(){
        val idSupplier = _idSupplier.value
        viewModelScope.launch {
            val result = supplierRepository.deleteSupplier(idSupplier.toString())
            _isStatusDelete.value = result
        }
    }
}