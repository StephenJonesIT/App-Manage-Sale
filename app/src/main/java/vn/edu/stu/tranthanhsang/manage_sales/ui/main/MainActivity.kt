package vn.edu.stu.tranthanhsang.manage_sales.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import vn.edu.stu.tranthanhsang.manage_sales.R
import vn.edu.stu.tranthanhsang.manage_sales.databinding.ActivityMainBinding
import vn.edu.stu.tranthanhsang.manage_sales.ui.bill.BillFragment
import vn.edu.stu.tranthanhsang.manage_sales.ui.home.HomeFragment
import vn.edu.stu.tranthanhsang.manage_sales.ui.order.OrderFragment
import vn.edu.stu.tranthanhsang.manage_sales.ui.product.ProductFragment
import vn.edu.stu.tranthanhsang.manage_sales.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setPositionNavigation()
        addEvents()
    }

    private fun addEvents() {
        binding?.bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_order -> replaceFragment(OrderFragment())
                R.id.nav_package -> replaceFragment(ProductFragment())
                R.id.nav_receive -> replaceFragment(BillFragment())
                R.id.nav_add-> replaceFragment(SettingFragment())
                else -> {replaceFragment(HomeFragment())}
            }
        }
    }
    private fun setPositionNavigation(){
        val position = intent.getIntExtra("status_navigation",0)
        Log.d("DATA",position.toString())
            when(position) {
                1 -> {
                    binding?.bottomNavigation?.selectedItemId = R.id.nav_receive
                    replaceFragment(BillFragment())
                }
                2 ->{
                    binding?.bottomNavigation?.selectedItemId = R.id.nav_package
                    replaceFragment(ProductFragment())
                }
                3 ->{
                    binding?.bottomNavigation?.selectedItemId = R.id.nav_order
                    replaceFragment(OrderFragment())
                }
                4 -> {
                    binding?.bottomNavigation?.selectedItemId = R.id.nav_add
                    replaceFragment(SettingFragment())
                }
                else ->{
                    binding?.bottomNavigation?.selectedItemId = R.id.nav_home
                    replaceFragment(HomeFragment())
                }
            }
    }
    private fun replaceFragment(fragmentId: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main,fragmentId)
            .commit()
        return true
    }
}