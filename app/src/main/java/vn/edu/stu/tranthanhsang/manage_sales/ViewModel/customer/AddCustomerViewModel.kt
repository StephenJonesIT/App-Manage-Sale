package vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.CreateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.CreateCustomerRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.StringUtils

class AddCustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _idCustomer = MutableLiveData<String>()
    private val _nameCustomer = MutableLiveData<String>()
    private val _telephone = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _customerType = MutableLiveData<String>()

    private val _isCancelClicked = MutableLiveData<Boolean>()
    private val _isSuccessClicked = MutableLiveData<Boolean>()
    private val _isStatusCreate = MutableLiveData<Result<StatusResponse>>()

    val isCancelClicked: LiveData<Boolean> get() = _isCancelClicked
    val isSuccessClicked: LiveData<Boolean> get() = _isSuccessClicked
    val isStatusCreate: LiveData<Result<StatusResponse>> get() = _isStatusCreate

    val isCustomerType: LiveData<String> get() = _customerType
    val idCustomer: LiveData<String> get() = _idCustomer
    val nameCustomer: LiveData<String> get() = _nameCustomer
    val telephone: LiveData<String> get() = _telephone
    val address: LiveData<String> get() = _address

    init {
        _isSuccessClicked.value = false
        _isCancelClicked.value = false
    }
    fun onCancelClick() {
        _isCancelClicked.value = true
    }

    fun onSuccessClick() {
        _isSuccessClicked.value = true
    }
    fun setTokenViewModel(newToken: String){
        customerRepository.updateToken(newToken)
    }
    fun createCustomer(){
        val idCustomer = _idCustomer.value
        val nameCustomer = _nameCustomer.value
        val (Ho, Ten) = StringUtils.splitFullName(nameCustomer.toString())
        Log.d("Ho", Ho)
        Log.d("Ten", Ten)
        val telephone = _telephone.value
        val address = _address.value
        val customerType = _customerType.value
        viewModelScope.launch {
            val result = customerRepository.createCustomer(
                CreateCustomerRequest(
                    idCustomer.toString(),
                    Ho,
                    Ten,
                    telephone.toString(),
                    address.toString(),
                    customerType.toString()
                )
            )
            _isStatusCreate.value = result
        }
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
}