package vn.edu.stu.tranthanhsang.manage_sales.ui.employee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.Employee
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityEmployeeAddBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierListActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.AddEmployeeViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.AddEmployeeViewModelFactory

class EmployeeAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeAddBinding
    private lateinit var addEmployeeViewModel: AddEmployeeViewModel
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_add)
        tokenManager = TokenManage(this)
        val repository = EmployeeRepository()
        addEmployeeViewModel = ViewModelProvider(this,
            AddEmployeeViewModelFactory(repository)
        )[AddEmployeeViewModel::class.java]

        addEmployeeViewModel.setToken(tokenManager.getToken().toString())
        binding.addEmployee = addEmployeeViewModel
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/2, systemBars.top/2, systemBars.right/2, systemBars.bottom/2)
            insets
        }
        addEvents()
        observeEvents()
    }
    private fun addEvents() {
        binding.imgCancel.setOnClickListener {
            startActivity(Intent(this, SupplierListActivity::class.java))
        }
    }
    private fun observeEvents() {
        addEmployeeViewModel.isStatusSave.observe(this){
            if (it){
                addDataFromView()
                addEmployeeViewModel.createEmployee()
            }
        }
        addEmployeeViewModel.isStatusCreate.observe(this){
            if(it.isSuccess){
                startActivity(Intent(this, EmployeeListActivity::class.java))
                ToastUtils.showToast(this,"Thêm nhân viên thành công")
            }else{
                ToastUtils.showToast(this,"Thêm nhân viên thất bại")
            }
        }
    }
    private fun addDataFromView(){
        val idEmployee = binding.edtIdEmployee.text.toString()
        val nameEmployee = binding.edtNameEmployee.text.toString()
        val telephone = binding.edtTelephone.text.toString()
        val address = binding.edtAddress.text.toString()

        addEmployeeViewModel.setIdEmployee(idEmployee)
        addEmployeeViewModel.setNameEmployee(nameEmployee)
        addEmployeeViewModel.setTelephone(telephone)
        addEmployeeViewModel.setAddress(address)

    }
}