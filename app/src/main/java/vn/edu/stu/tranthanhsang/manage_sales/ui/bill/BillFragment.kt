package vn.edu.stu.tranthanhsang.manage_sales.ui.bill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.itemfunc.ItemFunction
import vn.edu.stu.tranthanhsang.manage_sales.databinding.FragmentBillBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.FragmentProductBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.itemfunc.ItemFunctionAdapter

class BillFragment : Fragment() {
    private lateinit var binding : FragmentBillBinding
    private lateinit var adapterFunc:ItemFunctionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            ItemFunction(R.drawable.ic_list_alt,"Danh sách phiếu nhập"),
            ItemFunction(R.drawable.ic_groups,"Thêm nhà cung cấp"),
            ItemFunction(R.drawable.ic_package, "Thêm sản phẩm")
        )
        adapterFunc = ItemFunctionAdapter(list){
            itemFunction ->
                when(itemFunction.title){
                    "Danh sách phiếu nhập" -> {
                    }
                    "Thêm nhà cung cấp" -> {
                    }
                    "Thêm sản phẩm" -> {
                    }
                }
        }
        binding.rcvEnterSlip.layoutManager = LinearLayoutManager(context)
        binding.rcvEnterSlip.adapter = adapterFunc
        val dividerItemDecoration = DividerItemDecoration(binding.rcvEnterSlip.context, LinearLayoutManager.VERTICAL)
        binding.rcvEnterSlip.addItemDecoration(dividerItemDecoration)
    }
}