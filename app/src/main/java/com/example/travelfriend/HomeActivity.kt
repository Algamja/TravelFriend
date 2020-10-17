package com.example.travelfriend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, HomeFragment()).commit()

        bottom_navigation_bar.selectedItemId = R.id.bottom_menu_home
        bottom_navigation_bar.setOnNavigationItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.bottom_menu_home -> {
                    transaction.replace(
                        R.id.fragment_layout,
                        HomeFragment()
                    ).commit()
                    false
                }
                else->{
                    false
                }
            }
        }
    }
}
