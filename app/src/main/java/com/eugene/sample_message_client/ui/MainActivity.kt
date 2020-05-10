package com.eugene.sample_message_client.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eugene.sample_message_client.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.navHost, RegisterFragment())
            .commit()
    }
}
