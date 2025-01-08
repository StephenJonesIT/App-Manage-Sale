package vn.edu.stu.tranthanhsang.manage_sales.ui.customer

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
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityCustomerListBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.customer.CustomerAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.CustomerListViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.CustomerListViewModelFactory

class CustomerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerListBinding
    private lateinit var listCustomerViewModel: CustomerListViewModel
    private lateinit var tokenManager: TokenManage
    private lateinit var adapter: CustomerAdapter

    private var currentPage = 1
    private val limit = 99
    private var isLoading = false
    private var hasMoreData = true
    private var isStatusFilter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tokenManager = TokenManage(this)
        val token = tokenManager.getToken()
        Log.v("TOKEN_SET", token.toString())

        val repository = CustomerRepository()

        listCustomerViewModel = ViewModelProvider(
            this,
            CustomerListViewModelFactory(repository)
        )[CustomerListViewModel::class.java]

        listCustomerViewModel.setTokenViewModel(token.toString())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_list)
        binding.customerListViewModel = listCustomerViewModel
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
        addObserve()

        listCustomerViewModel.fetchCustomersViewModel("", currentPage, limit)
    }

    private fun addViews() {
        binding.rcvCustomer.layoutManager = LinearLayoutManager(this)
        adapter = CustomerAdapter(mutableListOf()) { customer ->
            val intent = Intent(this, CustomerEditActivity::class.java)
            intent.putExtra("CUSTOMER", customer)
            startActivity(intent)
        }
        binding.rcvCustomer.adapter = adapter
        val dividerCustomerDecoration = DividerItemDecoration(
            binding.rcvCustomer.context,
            LinearLayoutManager.VERTICAL
        )
        binding.rcvCustomer.addItemDecoration(dividerCustomerDecoration)
    }

    private fun addObserve() {
        listCustomerViewModel.customers.observe(this) { result ->
            result.fold(
                onSuccess = { customerResponse ->
                    if (!isStatusFilter){
                    Log.d("DATA", customerResponse.data.toString())
                    adapter.addCustomers(customerResponse.data)
                    hasMoreData = customerResponse.data.size == limit
                    isLoading = false
                    }else{
                        isStatusFilter = false
                        adapter.filterType(customerResponse.data)
                        hasMoreData = customerResponse.data.size == limit
                        isLoading = false
                    }
                }, onFailure = {
                    isLoading = false
                    it.message?.let { it1 -> ToastUtils.showToast(this, it1) }
                }
            )
        }
    }

    private fun addEvents() {
        binding.imgReturn.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            intentMain.putExtra("status_navigation", 1)
            startActivity(intentMain)
        }

        binding.rdgContainer.setOnCheckedChangeListener{
            group, checkedId ->
            when(checkedId){
                R.id.rbd_normal -> {
                    isStatusFilter = true
                    listCustomerViewModel.fetchCustomersViewModel("Bình thường", currentPage,limit)
                }
                R.id.rbd_vip -> {
                    isStatusFilter = true
                    listCustomerViewModel.fetchCustomersViewModel("VIP", currentPage,limit)
                }
            }
        }

        binding.rcvCustomer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                setupNextPage(layoutManager)
            }
        })

        binding.searchView.setOnClickListener {
            val layoutManager = binding.rcvCustomer.layoutManager as LinearLayoutManager
            setupNextPage(layoutManager)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }
        })

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this,CustomerAddActivity::class.java))
        }
    }

    private fun setupNextPage(layoutManager: LinearLayoutManager) {
        if (!isLoading && hasMoreData && layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
            currentPage++
            isLoading = true
            listCustomerViewModel.fetchCustomersViewModel("",currentPage, limit)
        }
    }
}