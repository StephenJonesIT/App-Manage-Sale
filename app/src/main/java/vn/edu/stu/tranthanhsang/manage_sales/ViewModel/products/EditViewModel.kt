package vn.edu.stu.tranthanhsang.manage_sales.viewModel.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.UpdateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository

class EditViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val idProduct = MutableLiveData<String>()
    val nameProduct = MutableLiveData<String>()
    val priceProduct = MutableLiveData<String>()
    val slProduct = MutableLiveData<String>()
    val typeProduct = MutableLiveData<String>()
    val dvtProduct = MutableLiveData<String>()

    private val _isStatusEdit = MutableLiveData<Boolean>()
    private val _isCancelClicked = MutableLiveData<Boolean>()
    private val _isConfirmClicked = MutableLiveData<Boolean>()
    private val _isDeleteClicked = MutableLiveData<Boolean>()
    private val _isStatusDelete = MutableLiveData<Result<StatusResponse>>()
    private val _isStatusUpdate = MutableLiveData<Result<StatusResponse>>()

    val isStatusEdit: LiveData<Boolean> get() = _isStatusEdit
    val isCancel: LiveData<Boolean> get() = _isCancelClicked
    val isConfirm: LiveData<Boolean> get() = _isConfirmClicked
    val isDelete: LiveData<Boolean> get() = _isDeleteClicked
    val isStatusDelete: LiveData<Result<StatusResponse>> get() = _isStatusDelete
    val isStatusUpdate: LiveData<Result<StatusResponse>> get() = _isStatusUpdate

    init {
        _isStatusEdit.value = false
        _isDeleteClicked.value = false
    }
    fun onStatusEditClicked() {
        _isStatusEdit.value = true
    }
    fun onDeleteClicked(){
        _isDeleteClicked.value = true
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

    fun deletedProduct(idProduct: String) {
        viewModelScope.launch {
            val result = productRepository.deleteProduct(idProduct)
            _isStatusDelete.value = result
        }
    }

    fun setTokenViewModel(newToken: String){
        productRepository.setToken(newToken)
    }

    fun updateProduct(){
        val id = idProduct.value
        val name = nameProduct.value
        val price = priceProduct.value
        val sl = slProduct.value
        val type = typeProduct.value
        val dvt = dvtProduct.value
        Log.d("ID",id.toString())
        Log.d("NAME",name.toString())
        Log.d("PRICE",price.toString())
        Log.d("SL",sl.toString())
        Log.d("TYPE",type.toString())
        Log.d("DVT",dvt.toString())

        viewModelScope.launch {
            val result = productRepository.updateProduct(
                id.toString(),
                UpdateProductRequest(
                    name.toString(),
                    sl?.toInt() ?: 0,
                    price?.toInt() ?: 0,
                    type?.toInt() ?: 3,
                    dvt.toString()
                )
            )
            _isStatusUpdate.value = result
        }
    }
}