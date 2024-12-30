package vn.edu.stu.tranthanhsang.manage_sales.viewModel.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.AuthRepository

class LoginViewModelFactory(
    private val application: Application,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
