package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.products

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.data.model.products.Product
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemProductBinding
import vn.edu.stu.tranthanhsang.manage_sales.utils.PriceUtils

class ProductAdapter(
    private var products: MutableList<Product>,
    private val itemClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(item: Product) {
            binding.tvTensp.text = item.TenSP
            binding.tvDonGia.text = "Giá: ${PriceUtils.formatNumber(item.DonGia)}"
            binding.tvSoLuong.text = "Tồn kho: ${item.SoLuong}"
            binding.tvTrangThai.text = item.TrangThai

            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }
    private val filteredItems = products.toMutableList()

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?) {
        filteredItems.clear()
        if (query.isNullOrBlank()) {
            filteredItems.addAll(products)
        } else {
            filteredItems.addAll(products.filter {
                it.TenSP.contains(query, ignoreCase = true)
            })
        }
        notifyDataSetChanged ()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(parent: ProductViewHolder, position: Int) {
        val item = filteredItems.get(position)
        parent.bind(item)
    }

    fun addProducts(newProducts: List<Product>) {
        val startPosition = products.size
        products.addAll(newProducts)
        filterList("")
        notifyItemRangeInserted (startPosition, newProducts.size)
    }
}