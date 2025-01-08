package vn.edu.stu.tranthanhsang.manage_sales.ui.customer

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.Customer
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.CustomerRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityCustomerEditBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.CustomDialogBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.product.ProductListActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.EditCustomerViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.customer.EditCustomerViewModelFactory

class CustomerEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerEditBinding
    private lateinit var editCustomerViewModel: EditCustomerViewModel
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = CustomerRepository()
        tokenManager = TokenManage(this)
        val token = tokenManager.getToken()
        Log.d("TOKEN", token.toString())

        editCustomerViewModel = ViewModelProvider(
            this,
            EditCustomerViewModelFactory(repository)
        )[EditCustomerViewModel::class.java]

        editCustomerViewModel.setTokenViewModel(token.toString())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_edit)
        binding.editCustomerViewModel = editCustomerViewModel
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
        addEvents()
        observeEvents()
        getDataFromIntent()
    }

    private fun observeEvents() {
        editCustomerViewModel.isStatusEdit.observe(this) {
            if (it) {
                setMessageTitle("Sửa khách hàng")
                setStatusEvents(View.INVISIBLE, View.VISIBLE)
                setEnabledEditText(true)
                setColorEditText("#FF000000")
            } else {
                setMessageTitle("Chi tiết khách hàng")
                setStatusEvents(View.VISIBLE, View.INVISIBLE)
                setEnabledEditText(false)
                setColorEditText("#797979")
            }
        }

        editCustomerViewModel.isCustomerType.observe(this) {
            when (it) {
                "VIP" -> binding.rbdVip.isChecked = true
                "Bình thường" -> binding.rbdNormal.isChecked = true
            }
        }

        editCustomerViewModel.isCancel.observe(this){
            if (it){
                showDialogOption(
                    "Hủy Sửa Khách Hàng",
                    "Bạn có muốn dừng việc sửa thông tin khách hàng không?"
                )
        }
        }

        editCustomerViewModel.isConfirm.observe(this){
            setValueViewModel()
            if (it){
                editCustomerViewModel.updateCustomer()
            }
        }
        editCustomerViewModel.isStatusUpdate.observe(this){
            if (it.isSuccess) {
                ToastUtils.showToast(this, "Sửa khách hàng thành công")
                startActivity(Intent(this, CustomerListActivity::class.java))
            } else ToastUtils.showToast(this, "Sửa khách hàng thất bại")
        }
    }

    private fun addEvents() {
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, CustomerListActivity::class.java))
        }
        binding.rdgContainer.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbd_vip -> editCustomerViewModel.setCustomerType("VIP")
                R.id.rbd_normal -> editCustomerViewModel.setCustomerType("Bình thường")
            }
        }
    }

    private fun setStatusEvents(visible: Int, invisible: Int) {
        binding.imgEdit.visibility = visible
        binding.imgBack.visibility = visible
        binding.imgCancel.visibility = invisible
        binding.imgOk.visibility = invisible
        binding.btnLuu.visibility = invisible
    }

    private fun setMessageTitle(message: String) {
        binding.tvTitle.text = message
    }

    private fun setEnabledEditText(status: Boolean) {
        binding.edtIdCustomer.isEnabled = false
        binding.edtNameCustomer.isEnabled = status
        binding.rbdVip.isEnabled = status
        binding.rbdNormal.isEnabled = status
        binding.edtTelephone.isEnabled = status
        binding.edtAddress.isEnabled = status
    }

    private fun setColorEditText(color: String) {
        binding.edtIdCustomer.setTextColor(Color.parseColor("#797979"))
        binding.edtNameCustomer.setTextColor(Color.parseColor(color))
        binding.edtTelephone.setTextColor(Color.parseColor(color))
        binding.edtAddress.setTextColor(Color.parseColor(color))
        binding.rbdVip.setTextColor(Color.parseColor(color))
        binding.rbdNormal.setTextColor(Color.parseColor(color))
    }

    private fun showDialogOption(title: String, message: String) {
        val dialogAlert = AlertDialog.Builder(this)
        val binding: CustomDialogBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.custom_dialog,
            null,
            false
        )

        dialogAlert.setView(binding.root)
        val dialog = dialogAlert.create()
        binding.tvTitle.text = title
        binding.tvMessage.text = message
        binding.tvExit.setOnClickListener {
            dialog.dismiss()
        }
        binding.tvConfirm.setOnClickListener {
            editCustomerViewModel.resetStatusEdit()
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun getDataFromIntent() {
        val customer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Customer>("CUSTOMER", Customer::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Customer>("CUSTOMER")
        }
        customer.let {
            editCustomerViewModel.setIdCustomer(it?.MaKH ?: "")
            editCustomerViewModel.setNameCustomer("${it?.Ho+" "+it?.Ten}")
            editCustomerViewModel.setTelephone(it?.SDT ?: "")
            editCustomerViewModel.setAddress(it?.DiaChi ?: "")
            editCustomerViewModel.setCustomerType(it?.LoaiKH ?: "Bình thường")
        }
    }

    private fun setValueViewModel() {
        editCustomerViewModel.setIdCustomer(binding.edtIdCustomer.text.toString())
        editCustomerViewModel.setNameCustomer(binding.edtNameCustomer.text.toString())
        editCustomerViewModel.setTelephone(binding.edtTelephone.text.toString())
        editCustomerViewModel.setAddress(binding.edtAddress.text.toString())
    }
}