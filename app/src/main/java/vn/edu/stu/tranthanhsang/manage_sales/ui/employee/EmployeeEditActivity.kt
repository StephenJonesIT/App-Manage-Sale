package vn.edu.stu.tranthanhsang.manage_sales.ui.employee

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
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.Employee
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.Supplier
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.EmployeeRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityEmployeeEditBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.CustomDialogBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierListActivity
import vn.edu.stu.tranthanhsang.manage_sales.utils.ToastUtils
import vn.edu.stu.tranthanhsang.manage_sales.utils.TokenManage
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.EditEmployeeViewModel
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.employee.EditEmployeeViewModelFactory

class EmployeeEditActivity : AppCompatActivity() {
    private lateinit var editEmployee: EditEmployeeViewModel
    private lateinit var binding: ActivityEmployeeEditBinding
    private lateinit var tokenManager: TokenManage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_employee_edit)
        val repository = EmployeeRepository()
        editEmployee = ViewModelProvider(this,
            EditEmployeeViewModelFactory(repository)
        )[EditEmployeeViewModel::class.java]

        tokenManager = TokenManage(this)
        editEmployee.setToken(tokenManager.getToken().toString())
        binding.editEmployee = editEmployee
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addEvents()
        observeEvents()
        getDataFromIntent()
    }
    private fun observeEvents() {
        editEmployee.isStatusEdit.observe(this){
            if(it){
                setMessageTitle("Sửa nhân viên")
                setStatusEvents(View.INVISIBLE, View.VISIBLE)
                setEnabledEditText(true)
                setColorEditText("#FF000000")
            }else{
                setMessageTitle("Chi tiết nhân viên")
                setStatusEvents(View.VISIBLE, View.INVISIBLE)
                setEnabledEditText(false)
                setColorEditText("#797979")
            }
        }
        editEmployee.isStatusUpdate.observe(this){
            if(it.isSuccess){
                ToastUtils.showToast(this,"Sửa nhân viên thành công")
                startActivity(Intent(this, EmployeeListActivity::class.java))
            }else{
                ToastUtils.showToast(this,"Sửa nhân viên thất bại")
            }
        }
        editEmployee.isConfirm.observe(this){
            if(it){
                setValueViewModel()
                editEmployee.updateEmployee()
            }
        }
        editEmployee.isStatusDelete.observe(this){
            if(it.isSuccess){
                ToastUtils.showToast(this,"Xóa nhân viên thành công")
                startActivity(Intent(this, EmployeeListActivity::class.java))
            }else{
                ToastUtils.showToast(this,"Xóa nhân viên thất bại")
            }
        }
    }

    private fun addEvents() {
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, EmployeeListActivity::class.java))
        }
        binding.imgCancel.setOnClickListener {
            showDialogOption("Hủy sửa nhân viên","Bạn có muốn dừng việc sửa thông tin nhân viên không?")
        }
        binding.btnDelete.setOnClickListener {
            showDialogOption("Xóa nhân viên","Bạn chắc chắn muốn xóa nhân viên này không?")
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
                "Xóa nhân viên" -> {
                    editEmployee.deleteEmployee()
                }
                "Hủy sửa nhân viên" -> {
                    editEmployee.resetStatusEdit()
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
        binding.edtIdEmployee.isEnabled = false
        binding.edtNameEmployee.isEnabled = status
        binding.edtTelephone.isEnabled = status
        binding.edtAddress.isEnabled = status

    }

    private fun setColorEditText(color: String) {
        binding.edtIdEmployee.setTextColor(Color.parseColor("#797979"))
        binding.edtNameEmployee.setTextColor(Color.parseColor(color))
        binding.edtTelephone.setTextColor(Color.parseColor(color))
        binding.edtAddress.setTextColor(Color.parseColor(color))
    }

    private fun setValueViewModel() {
        val id = binding.edtIdEmployee.text.toString()
        val name = binding.edtNameEmployee.text.toString()
        val telephone = binding.edtTelephone.text.toString()
        val address = binding.edtAddress.text.toString()

        editEmployee.setIdEmployee(id)
        editEmployee.setNameEmployee(name)
        editEmployee.setTelephone(telephone)
        editEmployee.setAddress(address)
    }
    private fun getDataFromIntent() {
        val employee = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EMPLOYEE", Employee::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EMPLOYEE")
        }
        employee.let {
            editEmployee.setIdEmployee(it?.MaNV ?: "")
            editEmployee.setNameEmployee("${it?.Ho}"+" ${it?.Ten}")
            editEmployee.setTelephone(it?.SDT ?: "")
            editEmployee.setAddress(it?.DiaChi ?: "")
        }
    }
}