package vn.edu.stu.tranthanhsang.manage_sales.data.model.customer

import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Filter
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Paging

data class CustomerResponse(
    val data: List<Customer>,
    val paging: Paging,
    val filter: Filter
)
