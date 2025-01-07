package vn.edu.stu.tranthanhsang.manage_sales.viewModel.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.CreateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.StatusResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils

class AddViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val idProduct = MutableLiveData<String>()
    val nameProduct = MutableLiveData<String>()
    val priceProduct = MutableLiveData<String>()
    val slProduct = MutableLiveData<String>()
    val typeProduct = MutableLiveData<String>()
    val dvtProduct = MutableLiveData<String>()

    private val _isCancelClicked = MutableLiveData<Boolean>()
    private val _isSuccessClicked = MutableLiveData<Boolean>()
    private val _isStatusCreate = MutableLiveData<Result<StatusResponse>>()

    val isCancelClicked: LiveData<Boolean> get() = _isCancelClicked
    val isSuccessClicked: LiveData<Boolean> get() = _isSuccessClicked
    val isStatusCreate: LiveData<Result<StatusResponse>> get() = _isStatusCreate

    init {
        _isSuccessClicked.value = false
        _isCancelClicked.value = false
    }
    fun onCancelClick() {
        _isCancelClicked.value = true
    }
    fun resetCancelClick(){
        _isCancelClicked.value = false
    }
    fun onSuccessClick() {
        _isSuccessClicked.value = true
    }
    fun setTokenViewModel(newToken: String){
        productRepository.setToken(newToken)
    }
    fun createProduct(){
        val id = idProduct.value
        val name = nameProduct.value
        val price = priceProduct.value?.let { PriceUtils.removeCommas(it) }
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
            val result = productRepository.createProduct(
                CreateProductRequest(
                    id.toString(),
                    name.toString(),
                    sl?.toInt() ?: 0,
                    price?.toInt()  ?: 0,
                    type?.toInt() ?: 3,
                    dvt.toString()
                )
            )
            _isStatusCreate.value= result
        }
    }
}