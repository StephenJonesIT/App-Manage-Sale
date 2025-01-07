package vn.edu.stu.tranthanhsang.manage_sales.ui.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.CreateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityProductAddBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.AddProductViewModelFactory
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.AddViewModel

class ProductAddActivity : AppCompatActivity() {
    private lateinit var addViewModel: AddViewModel

    private lateinit var binding: ActivityProductAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productRepository = ProductRepository()
        val token = getToken()
        Log.v("TOKEN_SET",token.toString())
        addViewModel = ViewModelProvider(
            this,AddProductViewModelFactory(productRepository)
        )[AddViewModel::class.java]

        addViewModel.setTokenViewModel(token.toString())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add)
        binding.addProductViewModel = addViewModel
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/2, systemBars.top, systemBars.right/2, systemBars.bottom)
            insets
        }
        observerEvents()
    }

    private fun observerEvents(){
        addViewModel.isCancelClicked.observe(this){
            if(it){
                val intentMain = Intent(this, MainActivity::class.java)
                intentMain.putExtra("status_navigation",2)
                startActivity(intentMain)
                addViewModel.resetCancelClick()
            }
        }
        addViewModel.isSuccessClicked.observe(this){
            if (it){
                addViewModel.idProduct.value = binding.edtIdProduct.text.toString()
                addViewModel.nameProduct.value = binding.edtNameProduct.text.toString()
                addViewModel.priceProduct.value = binding.edtPriceProduct.text.toString()
                addViewModel.slProduct.value = binding.edtSlProduct.text.toString()
                addViewModel.typeProduct.value = binding.edtLoaiProduct.text.toString()
                addViewModel.dvtProduct.value = binding.edtDvtProduct.text.toString()

                addViewModel.createProduct()
            }
        }
        addViewModel.isStatusCreate.observe(this){
            if (it.isSuccess){
                Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProductListActivity::class.java))
            }else{
                Toast.makeText(this,"Thêm thất bại",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token",null)
        Log.d("TOKEN",token.toString())
        return token
    }
}