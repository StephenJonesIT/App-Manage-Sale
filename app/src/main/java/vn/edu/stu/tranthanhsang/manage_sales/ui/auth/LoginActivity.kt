package vn.edu.stu.tranthanhsang.manage_sales.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityLoginBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.auth.LoginViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.auth.LoginViewModelFactory
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.AuthRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create AuthRepository
        val authRepository = AuthRepository()

        // Create ViewModelFactory
        val factory = LoginViewModelFactory(application, authRepository)

        // Create ViewModel using ViewModelFactory
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        // Data binding setup
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        // Observe loginResult LiveData
        loginViewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is LoginViewModel.LoginResult.Success -> {
                    // Handle success
                    showToast("Login successful!")
                    showLoadingIndicator(false)
                    navigateToMainActivity()
                }
                is LoginViewModel.LoginResult.Error -> {
                    // Handle error
                    showToast(result.message)
                    showLoadingIndicator(false)
                }
                is LoginViewModel.LoginResult.Loading -> {
                    showLoadingIndicator(true)
                }
            }
        })
    }

    private fun showLoadingIndicator(show: Boolean) {
        val loadingIndicator = findViewById<ProgressBar>(R.id.loading_indicator)
        loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: call finish if you want to close LoginActivity
    }
}
