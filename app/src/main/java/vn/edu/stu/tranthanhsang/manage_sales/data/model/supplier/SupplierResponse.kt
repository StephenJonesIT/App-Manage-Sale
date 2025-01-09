package vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier

import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Paging

data class SupplierResponse(
    val data: List<Supplier>,
    val paging: Paging
)
