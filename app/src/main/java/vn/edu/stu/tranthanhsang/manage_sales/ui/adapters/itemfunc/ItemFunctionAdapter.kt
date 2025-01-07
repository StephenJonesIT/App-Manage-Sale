package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.itemfunc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.data.model.itemfunc.ItemFunction
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemFunctionProductBinding

class ItemFunctionAdapter(
    private val list: List<ItemFunction>,
    private val itemClickListener: (ItemFunction) -> Unit
): RecyclerView.Adapter<ItemFunctionAdapter.ItemFuncViewHolder>() {
    inner class ItemFuncViewHolder(val binding: ItemFunctionProductBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemFunction){
            binding.imgIcon.setImageResource(item.icon)
            binding.tvTitle.text = item.title
            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemFuncViewHolder {
        var binding = ItemFunctionProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemFuncViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemFuncViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item)
    }
}