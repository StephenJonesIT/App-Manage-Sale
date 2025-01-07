package vn.edu.stu.tranthanhsang.manage_sales.data.model.products

import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Filter
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Paging

data class ProductResponse(
    val data: List<Product>,
    val paging: Paging,
    val filter: Filter
)
