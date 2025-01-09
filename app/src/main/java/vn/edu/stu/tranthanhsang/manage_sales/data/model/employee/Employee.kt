package vn.edu.stu.tranthanhsang.manage_sales.data.model.employee

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    val MaNV: String,
    val Ho: String,
    val Ten: String,
    val DiaChi: String,
    val SDT: String,
    val TrangThai: String
) : Parcelable
