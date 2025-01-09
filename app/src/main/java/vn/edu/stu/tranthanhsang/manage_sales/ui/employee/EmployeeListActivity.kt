package vn.edu.stu.tranthanhsang.manage_sales.ui.employee

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
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.Employee
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityEmployeeListBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivitySupplierListBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.employee.EmployeeAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.supplier.SupplierAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierAddActivity
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierEditActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.ListEmployeeViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.ListEmployeeViewModelFactory
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.ListSupplierViewModel

class EmployeeListActivity : AppCompatActivity() {
    private lateinit var listEmployeeViewModel: ListEmployeeViewModel
    private lateinit var binding: ActivityEmployeeListBinding
    private lateinit var adapter: EmployeeAdapter
    private lateinit var tokenManager: TokenManage

    private var currentPage = 1
    private val limit = 99
    private var isLoading = false
    private var hasMoreData = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = EmployeeRepository()
        tokenManager = TokenManage(this)
        Log.d("TOKEN", tokenManager.getToken().toString())

        listEmployeeViewModel = ViewModelProvider(
            this,
            ListEmployeeViewModelFactory(repository)
        )[ListEmployeeViewModel::class.java]

        listEmployeeViewModel.setTokenViewModel(tokenManager.getToken().toString())
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employee_list)
        binding.listEmployeeViewModel = listEmployeeViewModel
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/2, systemBars.top/2, systemBars.right/2, systemBars.bottom/2)
            insets
        }
        addViews()
        addEvents()
        observeEvents()
        listEmployeeViewModel.fetchEmployeesViewModel(currentPage,limit)
    }
    private fun addViews() {
        adapter = EmployeeAdapter(mutableListOf()){
            val intent = Intent(this, EmployeeEditActivity::class.java)
            intent.putExtra("EMPLOYEE", it)
            startActivity(intent)
        }
        binding.rcvEmployee.layoutManager = LinearLayoutManager(this)
        binding.rcvEmployee.adapter = adapter

        val dividerSupplierDecoration = DividerItemDecoration(binding.rcvEmployee.context,
            LinearLayoutManager.VERTICAL)
        binding.rcvEmployee.addItemDecoration(dividerSupplierDecoration)
    }

    private fun addEvents() {
        binding.imgReturn.setOnClickListener{
            val intentMain = Intent(this, MainActivity::class.java)
            intentMain.putExtra("status_navigation",0)
            startActivity(intentMain)
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, EmployeeAddActivity::class.java))
        }
        binding.searchView.setOnClickListener {
            val layoutManager = binding.rcvEmployee.layoutManager as LinearLayoutManager
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
        binding.rcvEmployee.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                setupNextPage(layoutManager)
            }
        })
    }
    private fun observeEvents() {
        listEmployeeViewModel.employees.observe(this){
            it.fold(onSuccess = { employeesResponse ->
                adapter.addEmployees(employeesResponse.data)
                isLoading = false
                hasMoreData = employeesResponse.data.size == limit
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
            listEmployeeViewModel.fetchEmployeesViewModel(currentPage,limit)
        }
    }
}