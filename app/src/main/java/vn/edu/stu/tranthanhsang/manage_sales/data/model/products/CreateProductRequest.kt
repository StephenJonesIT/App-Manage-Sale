package vn.edu.stu.tranthanhsang.manage_sales.data.model.products

data class CreateProductRequest(
    val MaSP: String,
    val TenSP: String,
    val SoLuong: Int,
    val DonGia: Int,
    val LoaiCay: Int,
    val DVT: String
)
