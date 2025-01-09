package vn.edu.stu.tranthanhsang.manage_sales.ui.supplier

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.Supplier
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.SupplierRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivitySupplierEditBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.CustomDialogBinding
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.EditProductViewModelFactory
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.EditSupplierViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.supplier.EditSupplierViewModelFactory

class SupplierEditActivity : AppCompatActivity() {
    private lateinit var editSupplier: EditSupplierViewModel
    private lateinit var binding: ActivitySupplierEditBinding
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_supplier_edit)

        val repository = SupplierRepository()
        editSupplier = ViewModelProvider(
            this,
            EditSupplierViewModelFactory(repository)
            )[EditSupplierViewModel::class.java]

        tokenManager = TokenManage(this)
        editSupplier.setToken(tokenManager.getToken().toString())
        binding.editSupplier = editSupplier
        binding.lifecycleOwner = this

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/2, systemBars.top/2, systemBars.right/2, systemBars.bottom/2)
            insets
        }
        addEvents()
        observeEvents()
        getDataFromIntent()
    }

    private fun observeEvents() {
        editSupplier.isStatusEdit.observe(this){
            if(it){
                setMessageTitle("Sửa nhà cung cấp")
                setStatusEvents(View.INVISIBLE, View.VISIBLE)
                setEnabledEditText(true)
                setColorEditText("#FF000000")
            }else{
                setMessageTitle("Chi tiết nhà cung cấp")
                setStatusEvents(View.VISIBLE,View.INVISIBLE)
                setEnabledEditText(false)
                setColorEditText("#797979")
            }
        }
        editSupplier.isStatusUpdate.observe(this){
            if(it.isSuccess){
                ToastUtils.showToast(this,"Sửa nhà cung cấp thành công")
                startActivity(Intent(this,SupplierListActivity::class.java))
            }else{
                ToastUtils.showToast(this,"Sửa nhà cung cấp thất bại")
            }
        }
        editSupplier.isConfirm.observe(this){
            if(it){
                setValueViewModel()
                editSupplier.updateSupplier()
            }
        }
        editSupplier.isStatusDelete.observe(this){
            if(it.isSuccess){
                ToastUtils.showToast(this,"Ngừng hợp tác nhà cung cấp thành công")
                startActivity(Intent(this,SupplierListActivity::class.java))
            }else{
                ToastUtils.showToast(this,"Ngừng hợp tác nhà cung cấp thất bại")
            }
        }
    }

    private fun addEvents() {
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this,SupplierListActivity::class.java))
        }
        binding.imgCancel.setOnClickListener {
            showDialogOption("Hủy sửa nhà cung cấp","Bạn có muốn dừng việc sửa thông tin nhà cung cấp không?")
        }
        binding.btnDelete.setOnClickListener {
            showDialogOption("Ngừng hợp tác","Bạn có muốn dừng việc hợp tác với nhà cung cấp này không?")
        }
    }
    private fun showDialogOption(title: String,message: String) {
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
            when(title){
                "Ngừng hợp tác" -> {
                    editSupplier.deleteSupplier()
                }
                "Hủy sửa nhà cung cấp" -> {
                    editSupplier.resetStatusEdit()
                }
            }
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun setStatusEvents(visible: Int, invisible: Int) {
        binding.imgEdit.visibility = visible
        binding.imgBack.visibility = visible
        binding.btnDelete.visibility = visible
        binding.imgCancel.visibility = invisible
        binding.imgOk.visibility = invisible
        binding.btnLuu.visibility = invisible
    }
    private fun setMessageTitle(message: String) {
        binding.tvTitle.text = message
    }
    private fun setEnabledEditText(status: Boolean) {
        binding.edtIdSupplier.isEnabled = false
        binding.edtNameSupplier.isEnabled = status
        binding.edtTelephone.isEnabled = status
        binding.edtAddress.isEnabled = status
        binding.edtType.isEnabled = status
    }

    private fun setColorEditText(color: String) {
        binding.edtIdSupplier.setTextColor(Color.parseColor("#797979"))
        binding.edtNameSupplier.setTextColor(Color.parseColor(color))
        binding.edtTelephone.setTextColor(Color.parseColor(color))
        binding.edtAddress.setTextColor(Color.parseColor(color))
        binding.edtType.setTextColor(Color.parseColor(color))
    }

    private fun setValueViewModel() {
        val id = binding.edtIdSupplier.text.toString()
        val name = binding.edtNameSupplier.text.toString()
        val telephone = binding.edtTelephone.text.toString()
        val address = binding.edtAddress.text.toString()
        val typeSupplier = binding.edtType.text.toString()

        editSupplier.setIdSupplier(id)
        editSupplier.setNameSupplier(name)
        editSupplier.setTelephone(telephone)
        editSupplier.setAddress(address)
        editSupplier.setSupplierType(typeSupplier)
    }
    private fun getDataFromIntent() {
        val supplier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("supplier", Supplier::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("supplier")
        }
        supplier.let {
            editSupplier.setIdSupplier(it?.MaNCC ?: "")
            editSupplier.setNameSupplier("${it?.Ho}"+" ${it?.Ten}")
            editSupplier.setTelephone(it?.SDT ?: "")
            editSupplier.setAddress(it?.DiaChi ?: "")
            editSupplier.setSupplierType(it?.LoaiNCC.toString())
        }
    }
}