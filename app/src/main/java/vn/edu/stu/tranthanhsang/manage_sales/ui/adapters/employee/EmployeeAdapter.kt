package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.employee

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.data.model.employee.Employee
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.Supplier
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemEmployeeBinding
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemSupplierBinding

class EmployeeAdapter(
    private val employees: MutableList<Employee>,
    private val itemClickListener: (Employee) -> Unit
): RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    inner class EmployeeViewHolder(val binding: ItemEmployeeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Employee){
            binding.tvTen.text = "${item.Ho}"+" ${item.Ten}"
            binding.tvAddress.text = item.DiaChi
            binding.tvTelephone.text = item.SDT

            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    private val filteredItems = employees.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(parent: EmployeeViewHolder, position: Int) {
        val item = filteredItems[position]
        parent.bind(item)
    }
    fun addEmployees(newProducts: List<Employee>) {
        val startPosition = employees.size
        employees.addAll(newProducts)
        filterList("")
        notifyItemRangeInserted (startPosition, newProducts.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?) {
        filteredItems.clear()
        if (query.isNullOrBlank()) {
            filteredItems.addAll(employees)
        } else {
            val searchquery = query.lowercase().trim()
            filteredItems.addAll(employees.filter { supplier ->
                supplier.Ho.lowercase().contains(searchquery) ||
                        supplier.Ten.lowercase().contains(searchquery) ||
                        supplier.SDT.lowercase().contains(searchquery) ||
                        supplier.DiaChi.lowercase().contains(searchquery)
            })
        }
        notifyDataSetChanged()
    }
}