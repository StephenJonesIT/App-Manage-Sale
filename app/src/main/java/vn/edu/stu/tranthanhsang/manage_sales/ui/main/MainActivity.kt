package vn.edu.stu.tranthanhsang.manage_sales.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
}