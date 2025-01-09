package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.SupplierResponse
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository

class ListSupplierViewModel(
    private val supplierRepository: SupplierRepository
) : ViewModel(){
    private val _suppliers = MutableLiveData<Result<SupplierResponse>>()
    val suppliers: LiveData<Result<SupplierResponse>> get() = _suppliers

    fun fetchSuppliersViewModel(page: Int, limit: Int) {
        viewModelScope.launch {
            val results = supplierRepository.fetchSuppliers(page, limit)
            _suppliers.value = results
        }
    }
    fun setTokenViewModel(newToken: String){
        supplierRepository.updateToken(newToken)
    }
}