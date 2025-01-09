package vn.edu.stu.tranthanhsang.manage_sales.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.home.ItemDashboard
import vn.edu.stu.tranthanhsang.manage_sales.databinding.FragmentHomeBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.home.ItemDashboardAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.employee.EmployeeListActivity
import vn.edu.stu.tranthanhsang.manage_sales.ui.product.ProductAddActivity
import vn.edu.stu.tranthanhsang.manage_sales.ui.supplier.SupplierAddActivity

class HomeFragment : Fragment() {
private lateinit var binding:FragmentHomeBinding
private lateinit var adapter: ItemDashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            ItemDashboard(R.drawable.ic_list_alt,"Tạo phiếu nhập ",R.color.blue_dashboard),
            ItemDashboard(R.drawable.ic_enhanced_encryption,"Thêm sản phẩm ",R.color.green_dashboard),
            ItemDashboard(R.drawable.ic_order_approve,"Tạo hóa đơn ",R.color.yellow_dashboard),
            ItemDashboard(R.drawable.ic_groups,"Quản lý nhân viên",R.color.yellow_dashboard),
            ItemDashboard(R.drawable.ic_account,"Quản lý tài khoản",R.color.blue_dashboard),
            ItemDashboard(R.drawable.ic_person,"Thêm nhà cung cấp",R.color.green_dashboard),
        )

        adapter = ItemDashboardAdapter(list){
            itemDashboard ->
                when(itemDashboard.title){
                    "Thêm sản phẩm " -> {
                        val intent = Intent(context, ProductAddActivity::class.java)
                        intent.putExtra("status",1)
                        startActivity(intent)
                    }
                    "Quản lý nhân viên" -> {
                        val intent = Intent(context, EmployeeListActivity::class.java)
                        intent.putExtra("status",1)
                        startActivity(intent)
                    }
                    "Thêm nhà cung cấp" -> {
                        val intent = Intent(context, SupplierAddActivity::class.java)
                        intent.putExtra("status",1)
                        startActivity(intent)
                    }
                    "Tạo phiếu nhập " -> {

                    }
                    "Tạo hóa đơn " -> {

                    }
                    "Quản lý tài khoản" -> {

                    }
                }
        }
        binding.rcvDashboard.layoutManager = GridLayoutManager(context,3)
        binding.rcvDashboard.adapter = adapter
        addEvents()
    }

    private fun addEvents() {
        binding.svDashboard.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit( query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filterList(query)
                return true
            }
        })
    }
}