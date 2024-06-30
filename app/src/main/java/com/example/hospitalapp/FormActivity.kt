package com.example.hospitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class FormActivity : AppCompatActivity() {

    private lateinit var rgIcuName: RadioGroup
    private lateinit var etStaffName: EditText
    private lateinit var etStaffPost: EditText
    private lateinit var etEntryTime: EditText
    private lateinit var etPurpose: EditText
    private lateinit var etNumObservations: EditText
    private lateinit var btnSubmitForm: Button
    private lateinit var tvStaffEntryLabel: TextView
    private lateinit var database: FirebaseDatabase

    private var entryId: String? = null
    private var numStaffEntries: Int = 0
    private var currentStaffEntry: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        tvStaffEntryLabel = findViewById(R.id.tvStaffEntryLabel)
        rgIcuName = findViewById(R.id.rgIcuName)
        etStaffName = findViewById(R.id.etStaffName)
        etStaffPost = findViewById(R.id.etStaffPost)
        etEntryTime = findViewById(R.id.etEntryTime)
        etPurpose = findViewById(R.id.etPurpose)
        etNumObservations = findViewById(R.id.etNumObservations)
        btnSubmitForm = findViewById(R.id.btnSubmitForm)
        database = FirebaseDatabase.getInstance()

        entryId = intent.getStringExtra("entryId") ?: database.reference.child("entries").push().key
        numStaffEntries = intent.getIntExtra("numStaffEntries", 0)
        currentStaffEntry = intent.getIntExtra("currentStaffEntry", 1)

        tvStaffEntryLabel.text = "Staff Entry $currentStaffEntry"

        btnSubmitForm.setOnClickListener {
            val selectedIcuRadioButtonId = rgIcuName.checkedRadioButtonId
            val selectedIcuRadioButton = findViewById<RadioButton>(selectedIcuRadioButtonId)
            val icuName = selectedIcuRadioButton?.text.toString()
            val staffName = etStaffName.text.toString()
            val staffPost = etStaffPost.text.toString()
            val entryTime = etEntryTime.text.toString()
            val purpose = etPurpose.text.toString()
            val numObservations = etNumObservations.text.toString().toIntOrNull() ?: 0

            if (icuName.isEmpty() || staffName.isEmpty() || staffPost.isEmpty() || entryTime.isEmpty() || purpose.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val staffEntry = StaffEntryData(icuName, staffName, staffPost, entryTime, purpose)
            val ref = database.reference.child("entries").child(entryId!!).child("staffEntries").push()
            ref.setValue(staffEntry).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (numObservations > 0) {
                        val intent = Intent(this, QuestionnaireActivity::class.java)
                        intent.putExtra("entryId", entryId)
                        intent.putExtra("staffEntryId", ref.key)
                        intent.putExtra("numObservations", numObservations)
                        intent.putExtra("currentObservation", 1)
                        intent.putExtra("numStaffEntries", numStaffEntries)
                        intent.putExtra("currentStaffEntry", currentStaffEntry)
                        startActivity(intent)
                    } else {
                        navigateToNextFormOrFinish()
                    }
                } else {
                    Toast.makeText(this, "Failed to submit data. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToNextFormOrFinish() {
        if (currentStaffEntry < numStaffEntries) {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("entryId", entryId)
            intent.putExtra("numStaffEntries", numStaffEntries)
            intent.putExtra("currentStaffEntry", currentStaffEntry + 1)
            startActivity(intent)
        } else {
            Toast.makeText(this, "All staff entries completed.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {

        // Show a toast message instead of navigating back
        Toast.makeText(this, "Please fill in the details!!", Toast.LENGTH_SHORT).show()

        // Uncomment the line below if you want to prevent back navigation completely

    }
}

data class StaffEntryData(
    val icuNumber: String,
    val staffName: String,
    val staffPost: String,
    val entryTime: String,
    val purpose: String
)
