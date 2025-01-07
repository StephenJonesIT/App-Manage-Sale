package vn.edu.stu.tranthanhsang.manage_sales.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.UpdateProductRequest
import vn.edu.stu.tranthanhsang.manage_sales.data.repository.ProductRepository
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityProductEditBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.CustomDialogBinding
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.EditProductViewModelFactory
import vn.edu.stu.tranthanhsang.manage_sales.viewModel.products.EditViewModel

class ProductEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductEditBinding
    private lateinit var editViewModel: EditViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val productRepository = ProductRepository()
        val token = getToken()
        Log.v("TOKEN_SET",token.toString())

        editViewModel = ViewModelProvider(
            this,
            EditProductViewModelFactory(
                productRepository
            )
        )[EditViewModel::class.java]

        editViewModel.setTokenViewModel(token.toString())

        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_edit)
        binding.editProductViewModel = editViewModel
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left/2, systemBars.top, systemBars.right/2, systemBars.bottom)
            insets
        }
        addEvents()
        observerEvents()
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Product>("PRODUCT", Product::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Product>("PRODUCT")
        }
        product.let {
            editViewModel.idProduct.value =it?.MaSP
            editViewModel.nameProduct.value =it?.TenSP
            editViewModel.priceProduct.value = it?.DonGia?.let { it1 -> PriceUtils.formatNumber(it1) }
            editViewModel.slProduct.value = it?.SoLuong.toString()
            editViewModel.typeProduct.value = it?.LoaiCay.toString()
            editViewModel.dvtProduct.value = it?.DVT
        }
    }

    private fun addEvents() {
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }


    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun observerEvents() {
        editViewModel.isStatusEdit.observe(this){
            if (it){
                binding.imgCancel.visibility = View.VISIBLE
                binding.imgOk.visibility = View.VISIBLE
                binding.imgEdit.visibility = View.INVISIBLE
                binding.imgBack.visibility = View.INVISIBLE
                binding.btnLuu.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.INVISIBLE
                binding.tvTitle.text = "Sửa sản phẩm"

                binding.edtNameProduct.isEnabled = true
                binding.edtPriceProduct.isEnabled = true
                binding.edtSlProduct.isEnabled = true
                binding.edtLoaiProduct.isEnabled = true
                binding.edtDvtProduct.isEnabled = true

                binding.edtNameProduct.setTextColor(Color.parseColor("#FF000000"))
                binding.edtPriceProduct.setTextColor(Color.parseColor("#FF000000"))
                binding.edtSlProduct.setTextColor(Color.parseColor("#FF000000"))
                binding.edtLoaiProduct.setTextColor(Color.parseColor("#FF000000"))
                binding.edtDvtProduct.setTextColor(Color.parseColor("#FF000000"))

                editViewModel.priceProduct.value = PriceUtils.removeCommas(binding.edtPriceProduct.text.toString())
            }else {
                binding.imgEdit.visibility = View.VISIBLE
                binding.imgBack.visibility = View.VISIBLE
                binding.imgCancel.visibility = View.INVISIBLE
                binding.imgOk.visibility = View.INVISIBLE
                binding.tvTitle.text = "Chi tiết sản phẩm"
                binding.btnLuu.visibility = View.INVISIBLE
                binding.btnDelete.visibility = View.VISIBLE

                binding.edtIdProduct.isEnabled = false
                binding.edtNameProduct.isEnabled = false
                binding.edtPriceProduct.isEnabled = false
                binding.edtSlProduct.isEnabled = false
                binding.edtLoaiProduct.isEnabled = false
                binding.edtDvtProduct.isEnabled = false

                binding.edtIdProduct.setTextColor(Color.parseColor("#797979"))
                binding.edtNameProduct.setTextColor(Color.parseColor("#797979"))
                binding.edtPriceProduct.setTextColor(Color.parseColor("#797979"))
                binding.edtSlProduct.setTextColor(Color.parseColor("#797979"))
                binding.edtLoaiProduct.setTextColor(Color.parseColor("#797979"))
                binding.edtDvtProduct.setTextColor(Color.parseColor("#797979"))

            }
        }
        editViewModel.isDelete.observe(this){
            if (it){
                showDialogOption(
                    "Ngưng Bán Sản Phẩm",
                    "Bạn có chắc chắn muốn ngưng kinh doanh sản phẩm này không?"
                )
            }
        }
        editViewModel.isCancel.observe(this){
            if (it){
                showDialogOption(
                    "Hủy Sửa Sản Phẩm",
                    "Bạn có muốn dừng việc sửa sản phẩm không?"
                )
            }
        }
        editViewModel.isStatusDelete.observe(this){
            if (it.isSuccess){
                Toast.makeText(this,"Ngưng kinh doanh sản phẩm thành công",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProductListActivity::class.java))
            }else{
                Toast.makeText(this,"Xử lý thất bại",Toast.LENGTH_SHORT).show()
            }
        }
        editViewModel.isConfirm.observe(this){
            if (it){
                editViewModel.idProduct.value = binding.edtIdProduct.text.toString()
                editViewModel.nameProduct.value = binding.edtNameProduct.text.toString()
                editViewModel.priceProduct.value = binding.edtPriceProduct.text.toString()
                editViewModel.slProduct.value = binding.edtSlProduct.text.toString()
                editViewModel.typeProduct.value = binding.edtLoaiProduct.text.toString()
                editViewModel.dvtProduct.value = binding.edtDvtProduct.text.toString()

                editViewModel.updateProduct()
            }
        }
        editViewModel.isStatusUpdate.observe(this){
            if (it.isSuccess){
                Toast.makeText(this,"Sửa sản phẩm thành công",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProductListActivity::class.java))
            }else{
                Toast.makeText(this,"Xử lý thất bại",Toast.LENGTH_SHORT).show()
            }
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
                "Ngưng Bán Sản Phẩm" -> {
                    val idProduct = editViewModel.idProduct.value
                    editViewModel.deletedProduct(idProduct.toString())
                }
                "Hủy Sửa Sản Phẩm" -> {
                    editViewModel.resetStatusEdit()
                }
            }
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token",null)
        Log.d("TOKEN",token.toString())
        return token
    }
}