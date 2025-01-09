package vn.edu.stu.tranthanhsang.manage_sales.ui.product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.itemfunc.ItemFunction
import vn.edu.stu.tranthanhsang.manage_sales.databinding.FragmentProductBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.itemfunc.ItemFunctionAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierListActivity

class ProductFragment : Fragment() {
private lateinit var binding: FragmentProductBinding
private lateinit var adapterFunc: ItemFunctionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val list = listOf(
            ItemFunction(R.drawable.ic_add_shopping_cart,"Danh sách sản phẩm"),
            ItemFunction(R.drawable.ic_person,"Nhà cung cấp")
        )

        adapterFunc = ItemFunctionAdapter(list){
            itemFunction ->
                when(itemFunction.title){
                    "Danh sách sản phẩm" -> {
                        startActivity(Intent(context,ProductListActivity::class.java))
                    }
                    "Nhà cung cấp" ->{
                        startActivity(Intent(context,SupplierListActivity::class.java))
                    }
                }
        }

        binding.rcvProduct.layoutManager = LinearLayoutManager(context)
        binding.rcvProduct.adapter = adapterFunc
        val dividerItemDecoration = DividerItemDecoration(binding.rcvProduct.context,LinearLayoutManager.VERTICAL)
        binding.rcvProduct.addItemDecoration(dividerItemDecoration)
        addEvents()
    }

    private fun addEvents() {
        binding.viewAdd.setOnClickListener {
            startActivity(Intent(context,ProductAddActivity::class.java))
        }
    }
}