package vn.edu.stu.tranthanhsang.manage_sales.ui.supplier

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivitySupplierListBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.supplier.SupplierAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.ListSupplierViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.ListSupplierViewModelFactory

class SupplierListActivity : AppCompatActivity() {
    private lateinit var listSupplierViewModel: ListSupplierViewModel
    private lateinit var binding: ActivitySupplierListBinding
    private lateinit var adapter: SupplierAdapter
    private lateinit var tokenManager: TokenManage

    private var currentPage = 1
    private val limit = 99
    private var isLoading = false
    private var hasMoreData = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = SupplierRepository()
        tokenManager = TokenManage(this)
        Log.d("TOKEN", tokenManager.getToken().toString())

        listSupplierViewModel = ViewModelProvider(
            this,
            ListSupplierViewModelFactory(repository)
        )[ListSupplierViewModel::class.java]

        listSupplierViewModel.setTokenViewModel(tokenManager.getToken().toString())

        binding = DataBindingUtil.setContentView(this,R.layout.activity_supplier_list)
        binding.listSupplier = listSupplierViewModel
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/3, systemBars.top/2, systemBars.right/2, systemBars.bottom/2)
            insets
        }
        addViews()
        addEvents()
        observeEvents()
        listSupplierViewModel.fetchSuppliersViewModel(currentPage,limit)
    }

    private fun addViews() {
        adapter = SupplierAdapter(mutableListOf()){
            val intent = Intent(this,SupplierEditActivity::class.java)
            intent.putExtra("supplier", it)
            startActivity(intent)
        }
        binding.rcvSupplier.layoutManager = LinearLayoutManager(this)
        binding.rcvSupplier.adapter = adapter

        val dividerSupplierDecoration = DividerItemDecoration(binding.rcvSupplier.context,LinearLayoutManager.VERTICAL)
        binding.rcvSupplier.addItemDecoration(dividerSupplierDecoration)
    }

    private fun addEvents() {
        binding.imgReturn.setOnClickListener{
            val intentMain = Intent(this,MainActivity::class.java)
            intentMain.putExtra("status_navigation",2)
            startActivity(intentMain)
        }
        binding.imgOption.setOnClickListener {
            startActivity(Intent(this,SupplierAddActivity::class.java))
        }
        binding.searchView.setOnClickListener {
            val layoutManager = binding.rcvSupplier.layoutManager as LinearLayoutManager
            setupNextPage(layoutManager)
        }
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }
        })
        binding.rcvSupplier.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                setupNextPage(layoutManager)
            }
        })
    }
    private fun observeEvents() {
        listSupplierViewModel.suppliers.observe(this){
            it.fold(onSuccess = { supplierResponse ->
                adapter.addSuppliers(supplierResponse.data)
                isLoading = false
                hasMoreData = supplierResponse.data.size == limit
            }, onFailure = { error ->
                isLoading = false
                ToastUtils.showToast(this,error.message.toString())
            })
        }
    }
    private fun setupNextPage(layoutManager: LinearLayoutManager) {
        if (!isLoading && hasMoreData && layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
            currentPage++
            isLoading = true
            listSupplierViewModel.fetchSuppliersViewModel(currentPage,limit)
        }
    }
}