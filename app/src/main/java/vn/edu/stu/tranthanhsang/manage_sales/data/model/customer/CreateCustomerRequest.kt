package vn.edu.stu.tranthanhsang.manage_sales.data.model.customer


data class CreateCustomerRequest (
    val MaKH: String,
    val Ho: String,
    val Ten: String,
    val SDT: String,
    val DiaChi: String,
    val LoaiKH: String,
)