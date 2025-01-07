package vn.edu.stu.tranthanhsang.manage_sales.ui.adapters.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.tranthanhsang.manage_sales.data.model.home.ItemDashboard
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ItemDashboardBinding

class ItemDashboardAdapter(
    private val items: List<ItemDashboard>,
    private val itemClickListener: (ItemDashboard) -> Unit
):RecyclerView.Adapter<ItemDashboardAdapter.ItemDashBoardViewHolder>() {
    inner class ItemDashBoardViewHolder(val binding: ItemDashboardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemDashboard){
            binding.imgDashboard.setImageResource(item.icon)
            binding.tvDashboard.text = HtmlCompat.fromHtml(addLineBreaks(item.title,2),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.viewDashboard.setBackgroundResource(item.color)
            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    private val filteredItems = ArrayList(items)

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String?) {
        filteredItems.clear()
        if (query.isNullOrBlank()) {
            filteredItems.addAll(items)
        } else {
            filteredItems.addAll(items.filter {
                it.title.contains(query, ignoreCase = true)
            })
        }
        notifyDataSetChanged ()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemDashBoardViewHolder {
        val binding = ItemDashboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemDashBoardViewHolder(binding)
    }

    override fun getItemCount() = filteredItems.size

    override fun onBindViewHolder(parent: ItemDashBoardViewHolder, positon: Int) {
        val item = filteredItems.get(positon)
        parent.bind(item)
    }
    fun addLineBreaks(text: String, wordsPerLine: Int): String {
        val words = text.split(" ")
        val stringBuilder = StringBuilder()
        for (i in words.indices) {
            stringBuilder.append(words[i])
            if ((i + 1) % wordsPerLine == 0) {
                stringBuilder.append("<br>")
            } else {
                stringBuilder.append(" ")
            }
        }
        return stringBuilder.toString()
    }

}