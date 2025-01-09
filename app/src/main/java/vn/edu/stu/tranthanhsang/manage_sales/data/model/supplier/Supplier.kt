package vn.edu.stu.tranthanhsang.manage_sales.data.model.supplier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supplier(
    val MaNCC: String,
    val Ho: String,
    val Ten: String,
    val DiaChi: String,
    val SDT: String,
    val LoaiNCC: Int,
    val TrangThai: String
) : Parcelable
