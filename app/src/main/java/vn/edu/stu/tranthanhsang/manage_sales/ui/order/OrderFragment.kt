package vn.edu.stu.tranthanhsang.manage_sales.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.itemfunc.ItemFunction
import vn.edu.stu.tranthanhsang.manage_sales.databinding.FragmentOrderBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.itemfunc.ItemFunctionAdapter
import vn.edu.stu.tranthanhsang.manage_sales.ui.main.MainActivity

class OrderFragment : Fragment() {
private lateinit var binding : FragmentOrderBinding
private lateinit var adapterFunc: ItemFunctionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val list = listOf(
            ItemFunction(R.drawable.ic_list_alt,"Danh sách đơn hàng"),
            ItemFunction(R.drawable.ic_person,"Khách hàng")
        )
        adapterFunc = ItemFunctionAdapter(list){
            itemFunction ->
            when(itemFunction.title){
                "Danh sách đơn hàng" -> {

                }
                "Khách hàng"->{

                }                }
        }

        binding.rcvOrder.layoutManager = LinearLayoutManager(context)
        binding.rcvOrder.adapter = adapterFunc

        val dividerItemDecoration = DividerItemDecoration(binding.rcvOrder.context,LinearLayoutManager.VERTICAL)
        binding.rcvOrder.addItemDecoration(dividerItemDecoration)

    }
}