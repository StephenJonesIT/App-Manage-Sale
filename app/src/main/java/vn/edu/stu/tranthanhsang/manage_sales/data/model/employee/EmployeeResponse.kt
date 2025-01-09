package vn.edu.stu.tranthanhsang.manage_sales.data.model.employee

import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Filter
import vn.edu.stu.tranthanhsang.manage_sales.data.model.common.Paging

data class EmployeeResponse(
    val data: List<Employee>,
    val paging: Paging,
    val filter: Filter
)
