package vn.edu.stu.tranthanhsang.manage_sales.data.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val MaSP: String,
    val TenSP: String,
    val SoLuong: Int,
    val DonGia: Int,
    val LoaiCay: Int,
    val DVT: String,
    val TrangThai: String
):Parcelable
