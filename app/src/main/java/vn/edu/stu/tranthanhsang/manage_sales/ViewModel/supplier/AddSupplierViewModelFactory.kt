package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository

class AddSupplierViewModelFactory(
    private val supplierRepository: SupplierRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AddSupplierViewModel::class.java)){
            return AddSupplierViewModel(supplierRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}