package com.example.hospitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnGoToLogin: Button
    private lateinit var btnGoToSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGoToLogin = findViewById(R.id.btnGoToLogin)
        btnGoToSignup = findViewById(R.id.btnGoToSignup)

        btnGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnGoToSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onBackPressed() {

        // Show a toast message instead of navigating back
        Toast.makeText(this, "Exiting the App!!", Toast.LENGTH_SHORT).show()

        // Uncomment the line below if want to do back navigation completely
         super.onBackPressed()
    }
}
