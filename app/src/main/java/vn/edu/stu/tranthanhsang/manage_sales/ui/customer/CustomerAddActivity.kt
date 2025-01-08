package vn.edu.stu.tranthanhsang.manage_sales.ui.customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityCustomerAddBinding
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.AddCustomerViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.AddCustomerViewModelFactory

class CustomerAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerAddBinding
    private lateinit var addCustomer: AddCustomerViewModel
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = CustomerRepository()
        addCustomer = ViewModelProvider(
            this,
            AddCustomerViewModelFactory(repository)
        )[AddCustomerViewModel::class.java]

        tokenManager = TokenManage(this)
        val token = tokenManager.getToken()
        Log.d("TOKEN", token.toString())
        addCustomer.setTokenViewModel(token.toString())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_add)
        binding.addCustomerViewModel = addCustomer
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/3, systemBars.top/3, systemBars.right/3, systemBars.bottom/3)
            insets
        }
        addEvents()
        observeEvents()
    }

    private fun observeEvents() {
        addCustomer.isCustomerType.observe(this) {
            when (it) {
                "VIP" -> binding.rbdVip.isChecked = true
                "Bình thường" -> binding.rbdNormal.isChecked = true
            }
        }
        addCustomer.isSuccessClicked.observe(this){
            if (it){
                setValueViewModel()
                addCustomer.createCustomer()
            }
        }
        addCustomer.isCancelClicked.observe(this){
            if (it){
                startActivity(Intent(this, CustomerListActivity::class.java))
            }
        }
        addCustomer.isStatusCreate.observe(this){
            if (it.isSuccess){
                ToastUtils.showToast(this, "Thêm khách hàng thành công")
                startActivity(Intent(this, CustomerListActivity::class.java))
            }else ToastUtils.showToast(this, "Thêm khách hàng thất bại")
        }
    }

    private fun addEvents(){
        binding.rdgContainer.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbd_vip -> addCustomer.setCustomerType("VIP")
                R.id.rbd_normal -> addCustomer.setCustomerType("Bình thường")
            }
        }
    }
    private fun setValueViewModel() {
        addCustomer.setIdCustomer(binding.edtIdCustomer.text.toString())
        addCustomer.setNameCustomer(binding.edtNameCustomer.text.toString())
        addCustomer.setTelephone(binding.edtTelephone.text.toString())
        addCustomer.setAddress(binding.edtAddress.text.toString())
    }
}