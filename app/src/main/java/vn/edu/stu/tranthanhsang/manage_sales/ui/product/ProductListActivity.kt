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
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityProductListBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.products.ProductAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.ProductListViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.ProductListViewModelFactory

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var listProductViewModel: ProductListViewModel
    private lateinit var tokenManager: TokenManage
    private lateinit var adapter: ProductAdapter

    private var currentPage = 1
    private val limit = 10
    private var isLoading = false
    private var hasMoreData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        val productRepository = ProductRepository()
        tokenManager = TokenManage(this)
        val token = tokenManager.getToken()
        Log.v("TOKEN_SET", token.toString())
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
        listProductViewModel.fetchProductsViewModel(currentPage, limit)
    }

    private fun addViews() {
        binding.rcvProduct.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(mutableListOf()) { product ->
            val intent = Intent(this, ProductEditActivity::class.java)
            intent.putExtra("PRODUCT", product)
            startActivity(intent)
        }
        binding.rcvProduct.adapter = adapter

        val dividerProductDecoration =
            DividerItemDecoration(binding.rcvProduct.context, LinearLayoutManager.VERTICAL)
        binding.rcvProduct.addItemDecoration(dividerProductDecoration)
    }

    private fun addEvents() {
        binding.imgReturn.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            intentMain.putExtra("status_navigation", 2)
            startActivity(intentMain)
        }
        listProductViewModel.products.observe(this) { result ->
            result.fold(onSuccess = { productResponse ->
                Log.d("DATA", productResponse.data.toString())
                adapter.addProducts(productResponse.data)
                hasMoreData = productResponse.data.size == limit
                isLoading = false
            }, onFailure = { error ->
                isLoading = false
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
            )
        }
        binding.searchView.setOnClickListener {
            val layoutManager = binding.rcvProduct.layoutManager as LinearLayoutManager
            setupNextPage(layoutManager)
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }
        })

        binding.rcvProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                setupNextPage(layoutManager)
            }
        })

        listProductViewModel.isStatusCreate.observe(this){
            if(it){
                startActivity(Intent(this,ProductAddActivity::class.java))
            }
        }
    }

    private fun setupNextPage(layoutManager: LinearLayoutManager) {
        if (!isLoading && hasMoreData && layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
            currentPage++
            isLoading = true
            listProductViewModel.fetchProductsViewModel(currentPage, limit)
        }
    }
}