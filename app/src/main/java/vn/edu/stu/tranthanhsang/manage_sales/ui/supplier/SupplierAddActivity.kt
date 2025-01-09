package vn.edu.stu.tranthanhsang.manage_sales.ui.supplier

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivitySupplierAddBinding
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.AddSupplierViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.AddSupplierViewModelFactory

class SupplierAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierAddBinding
    private lateinit var addSupplierViewModel: AddSupplierViewModel
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = SupplierRepository()
        addSupplierViewModel = ViewModelProvider(
            this,
            AddSupplierViewModelFactory(
                repository
            )
        )[AddSupplierViewModel::class.java]
        tokenManager = TokenManage(this)
        addSupplierViewModel.setToken(tokenManager.getToken().toString())

        binding = DataBindingUtil.setContentView(this,R.layout.activity_supplier_add)
        binding.addSupplier = addSupplierViewModel
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
            startActivity(Intent(this,SupplierListActivity::class.java))
        }
    }
    private fun observeEvents() {
        addSupplierViewModel.isStatusSave.observe(this){
            if (it){
                addDataFromView()
                addSupplierViewModel.createSupplier()
            }
        }
        addSupplierViewModel.isStatusCreate.observe(this){
            if(it.isSuccess){
                startActivity(Intent(this,SupplierListActivity::class.java))
                ToastUtils.showToast(this,"Thêm nhà cung cấp thành công")
            }else{
                ToastUtils.showToast(this,"Thêm nhà cung cấp thất bại")
            }
        }
    }
    private fun addDataFromView(){
        val idSupplier = binding.edtIdSupplier.text.toString()
        val nameSupplier = binding.edtNameSupplier.text.toString()
        val telephone = binding.edtTelephone.text.toString()
        val address = binding.edtAddress.text.toString()
        val supplierType = binding.edtTypeSupplier.text.toString()

        addSupplierViewModel.setIdSupplier(idSupplier)
        addSupplierViewModel.setNameSupplier(nameSupplier)
        addSupplierViewModel.setTelephone(telephone)
        addSupplierViewModel.setAddress(address)
        addSupplierViewModel.setSupplierType(supplierType)
    }
}