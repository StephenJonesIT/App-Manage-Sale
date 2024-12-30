package vn.edu.stu.tranthanhsang.manage_sales.viewModel.auth

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.AuthRepository
import vn.edu.stu.tranthanhsang.manage_sales.data.model.auth.LoginRequest

class LoginViewModel(application: Application, private val authRepository: AuthRepository) : AndroidViewModel(application) {
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val phoneError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val loginResult = MutableLiveData<LoginResult>()

    sealed class LoginResult {
        object Success : LoginResult()
        data class Error(val message: String) : LoginResult()
        object Loading : LoginResult()
    }

    fun onLoginClicked() {
        val phone = phoneNumber.value
        val pass = password.value

        if (validateInput(phone, pass) && validatePhoneNumber(phone) && validatePassword(pass)) {
            loginResult.value = LoginResult.Loading // Set state to Loading
            performLogin(phone!!, pass!!)
        }
    }

    private fun validateInput(phone: String?, pass: String?): Boolean {
        phoneError.value = null // Clear any previous errors
        passwordError.value = null // Clear any previous errors
        return if (!phone.isNullOrEmpty() && !pass.isNullOrEmpty()) {
            true
        } else {
            if (phone.isNullOrEmpty()) {
                phoneError.value = "Phone number cannot be empty"
            }
            if (pass.isNullOrEmpty()) {
                passwordError.value = "Password cannot be empty"
            }
            false
        }
    }

    private fun validatePhoneNumber(phone: String?): Boolean {
        return if (phone != null && phone.length == 10) {
            true
        } else {
            phoneError.value = "Phone number must be at least 10 characters long"
            false
        }
    }

    private fun validatePassword(pass: String?): Boolean {
        return if (pass != null && pass.length >= 6) {
            true
        } else {
            passwordError.value = "Password must be at least 6 characters long"
            false
        }
    }

    private fun performLogin(phone: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(phone, password)
                val response = authRepository.login(loginRequest)

                if (response.isSuccess) {
                    val token = response.getOrNull()?.token
                    if (token != null) {
                        saveToken(token)
                        loginResult.value = LoginResult.Success
                    } else {
                        loginResult.value = LoginResult.Error("Token not found")
                    }
                } else {
                    loginResult.value = LoginResult.Error(response.exceptionOrNull()?.message ?: "Login failed")
                }
            } catch (e: Exception) {
                loginResult.value = LoginResult.Error(e.message ?: "Login failed")
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }
}
