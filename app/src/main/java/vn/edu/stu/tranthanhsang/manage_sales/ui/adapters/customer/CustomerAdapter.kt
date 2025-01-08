package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.customer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.data.model.customer.Customer
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemCustomerBinding

class CustomerAdapter(
    private val customers: MutableList<Customer>,
    private val itemClickListener: (Customer) -> Unit
): RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
    inner class CustomerViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Customer){
                binding.tvTen.text = "${item.Ho}"+" ${item.Ten}"
                binding.tvSoDienThoai.text = item.SDT
                binding.tvLoai.text = item.LoaiKH

                binding.root.setOnClickListener {
                    itemClickListener(item)
                }
            }
    }

    private val filteredItems = customers.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false

        )
        return CustomerViewHolder(binding)
    }

    override fun getItemCount()=filteredItems.size

    override fun onBindViewHolder(parent: CustomerViewHolder, position: Int) {
        val item = filteredItems[position]
        parent.bind(item)
    }

    fun addCustomers(newProducts: List<Customer>) {
        val startPosition = customers.size
        customers.addAll(newProducts)
        filterList("")
        notifyItemRangeInserted (startPosition, newProducts.size)
    }

    fun filterType(newProducts: List<Customer>){
        customers.clear()
        customers.addAll(newProducts)
        filterList("")
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?) {
        filteredItems.clear()
        if (query.isNullOrBlank()) {
            filteredItems.addAll(customers)
        } else {
            filteredItems.addAll(customers.filter {
                it.Ho.contains(query, ignoreCase = true)
                it.Ten.contains(query, ignoreCase = true)
            })
        }
        notifyDataSetChanged ()
    }
}