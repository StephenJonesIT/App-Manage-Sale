package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.CreateSupplierRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class AddSupplierViewModel(
    private val supplierRepository: SupplierRepository
) : ViewModel() {
    private val _isStatusSave = MutableLiveData<Boolean>()
    val isStatusSave: LiveData<Boolean> get() = _isStatusSave

    private val _isStatusCreate = MutableLiveData<Result<StatusResponse>>()
    val isStatusCreate: LiveData<Result<StatusResponse>> get() = _isStatusCreate

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

    fun createSupplier() {
        val idSupplier = _idSupplier.value
        val nameSupplier = _nameSupplier.value
        val (Ho, Ten) = StringUtils.splitFullName(nameSupplier.toString())
        val telephone = _telephone.value
        val address = _address.value
        val supplierType = _supplierType.value
        viewModelScope.launch {
            val result = supplierRepository.createSupplier(
                CreateSupplierRequest(
                    idSupplier.toString(),
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString(),
                    supplierType?.toInt()?:3
                )
            )
            _isStatusCreate.value = result
        }
    }
    fun setToken(newToken: String){
        supplierRepository.updateToken(newToken)
    }
    fun onclickSave(){
        _isStatusSave.value = true
    }
}