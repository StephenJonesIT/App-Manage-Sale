package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository

class ListSupplierViewModelFactory(
    private val supplierRepository: SupplierRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListSupplierViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListSupplierViewModel(supplierRepository) as T
        }
       throw IllegalArgumentException("Unknown ViewModel class")
    }
}