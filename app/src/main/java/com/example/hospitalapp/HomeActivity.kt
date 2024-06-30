package com.example.hospitalapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var btnBegin: Button
    private lateinit var btnTableGenerator: Button
    private lateinit var logout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnBegin = findViewById(R.id.btnBegin)
        btnTableGenerator = findViewById(R.id.btnTableGenerator)
        logout = findViewById(R.id.logout)

        btnBegin.setOnClickListener {
            showNumberInputDialog()
        }

        btnTableGenerator.setOnClickListener {
            startActivity(Intent(this, TableActivity::class.java))
        }

        logout.setOnClickListener{
            // Build the alert dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
            builder.setMessage("Do you want to Log Out?")

            // Set up the buttons
            builder.setPositiveButton("Yes") { dialog, which ->
                // Navigate back to previous screen
                super.onBackPressed()
            }
            builder.setNegativeButton("No") { dialog, which ->
                // Do nothing (stay on the current screen)
                dialog.dismiss()
            }

            // Create and show the dialog
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun showNumberInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Number of Staff Entries")

        // Set up the input
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK") { dialog, which ->
            val numStaffEntriesStr = input.text.toString()
            val numStaffEntries = numStaffEntriesStr.toIntOrNull()

            if (numStaffEntries != null && numStaffEntries > 0) {
                // Navigate to FormActivity
                val intent = Intent(this, FormActivity::class.java)
                intent.putExtra("numStaffEntries", numStaffEntries)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid number of staff entries.", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onBackPressed() {
        // Build the alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Do you want to Log Out?")

        // Set up the buttons
        builder.setPositiveButton("Yes") { dialog, which ->
            // Navigate back to previous screen
            super.onBackPressed()
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing (stay on the current screen)
            dialog.dismiss()
        }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }
}
