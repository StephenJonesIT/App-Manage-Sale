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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityProductListBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.products.ProductAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.ProductListViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.ProductListViewModelFactory

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var listProductViewModel: ProductListViewModel
    private lateinit var adapter: ProductAdapter
    private var currentPage = 1
    private val limit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        val productRepository = ProductRepository()
        val token = getToken()
        Log.v("TOKEN_SET",token.toString())
        listProductViewModel = ViewModelProvider(
            this, ProductListViewModelFactory(
                productRepository
            )
        )[ProductListViewModel::class.java]

        listProductViewModel.setTokenViewModel(token.toString())
        binding.listProductViewModel = listProductViewModel
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left / 2,
                systemBars.top / 2,
                systemBars.right / 2,
                systemBars.bottom / 2
            )
            insets
        }
        addViews()
        addEvents()
        listProductViewModel.fetchProductsViewModel(currentPage,limit)
    }

    private fun addViews() {
        binding.rcvProduct.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(mutableListOf()) { product ->
            val intent = Intent(this,ProductEditActivity::class.java)
            intent.putExtra("PRODUCT",product)
            startActivity(intent)
        }
        binding.rcvProduct.adapter = adapter

        val dividerProductDecoration = DividerItemDecoration(binding.rcvProduct.context,LinearLayoutManager.VERTICAL)
        binding.rcvProduct.addItemDecoration(dividerProductDecoration)
    }

    private fun addEvents() {
        binding.imgReturn.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            intentMain.putExtra("status_navigation",2)
            startActivity(intentMain)
        }
        listProductViewModel.products.observe(this) { result ->
            result.fold(onSuccess = { productResponse ->
                    Log.d("DATA",productResponse.data.toString())
                    adapter.addProducts(productResponse.data)
            }, onFailure = { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
            )
        }
    }

    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token",null)
        Log.d("TOKEN",token.toString())
        return token
    }
}