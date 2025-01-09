package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.supplier

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier.Supplier
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemSupplierBinding

class SupplierAdapter(
    private val suppliers: MutableList<Supplier>,
    private val itemClickListener: (Supplier) -> Unit
): RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {
    inner class SupplierViewHolder(val binding: ItemSupplierBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Supplier){
            binding.tvTen.text = "${item.Ho}"+" ${item.Ten}"
            binding.tvAddress.text = item.DiaChi
            binding.tvTelephone.text = item.SDT
            binding.tvType.text = "Loại "+item.LoaiNCC.toString()

            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    private val filteredItems = suppliers.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SupplierViewHolder {
        val binding = ItemSupplierBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SupplierViewHolder(binding)
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(parent: SupplierViewHolder, position: Int) {
        val item = filteredItems[position]
        parent.bind(item)
    }
    fun addSuppliers(newProducts: List<Supplier>) {
        val startPosition = suppliers.size
        suppliers.addAll(newProducts)
        filterList("")
        notifyItemRangeInserted (startPosition, newProducts.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?) {
        filteredItems.clear()
        if (query.isNullOrBlank()) {
            filteredItems.addAll(suppliers)
        } else {
            val searchquery = query.lowercase().trim()
            filteredItems.addAll(suppliers.filter { supplier ->
                supplier.Ho.lowercase().contains(searchquery) ||
                        supplier.Ten.lowercase().contains(searchquery) ||
                        supplier.SDT.lowercase().contains(searchquery) ||
                        supplier.DiaChi.lowercase().contains(searchquery)
            })
        }
        notifyDataSetChanged()
    }
}