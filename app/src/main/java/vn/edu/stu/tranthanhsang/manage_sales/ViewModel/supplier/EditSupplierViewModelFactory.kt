package vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository

class EditSupplierViewModelFactory(
    private val supplierRepository: SupplierRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(EditSupplierViewModel::class.java)){
            return EditSupplierViewModel(supplierRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}