package vn.edu.stu.tranthanhsang.manage_sales.viewModel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.ProductResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableLiveData<Result<ProductResponse>>()
    val products: LiveData<Result<ProductResponse>> get() = _products

    fun setTokenViewModel(newToken: String){
        productRepository.setToken(newToken)
    }
    fun fetchProductsViewModel(page: Int, limit: Int) {
        viewModelScope.launch {
            val results = productRepository.fetchProducts(page,limit)
            _products.value = results
        }
    }

}