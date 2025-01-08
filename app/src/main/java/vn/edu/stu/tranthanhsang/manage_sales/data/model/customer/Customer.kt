package vn.edu.stu.tranthanhsang.manage_sales.data.model.customer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer (
    val MaKH: String,
    val Ho: String,
    val Ten: String,
    val SDT: String,
    val DiaChi: String,
    val LoaiKH: String,
):Parcelable