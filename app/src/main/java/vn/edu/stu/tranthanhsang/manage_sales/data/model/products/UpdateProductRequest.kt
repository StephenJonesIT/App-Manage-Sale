package vn.edu.stu.tranthanhsang.manage_sales.data.model.products

data class UpdateProductRequest(
    val TenSP: String,
    val SoLuong: Int,
    val DonGia: Int,
    val LoaiCay: Int,
    val DVT: String
)
