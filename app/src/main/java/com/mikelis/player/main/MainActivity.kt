package com.mikelis.player.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikelis.player.R
import com.mikelis.player.main.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setMainFragment()
    }

    private fun setMainFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()
    }
}
