package vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier

data class UpdateSupplierRequest(
    val Ho: String,
    val Ten: String,
    val DiaChi: String,
    val SDT: String,
    val LoaiNCC: Int
)
