package com.example.hospitalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var etOperationNo: EditText
    private lateinit var rgBetPat: RadioGroup
    private lateinit var rgBefAsept: RadioGroup
    private lateinit var rgAftBF: RadioGroup
    private lateinit var rgAftPat: RadioGroup
    private lateinit var rgAftPSurr: RadioGroup
    private lateinit var rgGloves: RadioGroup
    private lateinit var btnSubmitQuestionnaire: Button
    private lateinit var database: FirebaseDatabase

    private var entryId: String? = null
    private var staffEntryId: String? = null
    private var numObservations: Int = 0
    private var currentObservation: Int = 0
    private var numStaffEntries: Int = 0
    private var currentStaffEntry: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        etOperationNo = findViewById(R.id.etOperationNo)
        rgBetPat = findViewById(R.id.rgBetPat)
        rgBefAsept = findViewById(R.id.rgBefAsept)
        rgAftBF = findViewById(R.id.rgAftBF)
        rgAftPat = findViewById(R.id.rgAftPat)
        rgAftPSurr = findViewById(R.id.rgAftPSurr)
        rgGloves = findViewById(R.id.rgGloves)
        btnSubmitQuestionnaire = findViewById(R.id.btnSubmitQuestionnaire)
        database = FirebaseDatabase.getInstance()

        entryId = intent.getStringExtra("entryId")
        staffEntryId = intent.getStringExtra("staffEntryId")
        numObservations = intent.getIntExtra("numObservations", 0)
        currentObservation = intent.getIntExtra("currentObservation", 1)
        numStaffEntries = intent.getIntExtra("numStaffEntries", 0)
        currentStaffEntry = intent.getIntExtra("currentStaffEntry", 1)

        btnSubmitQuestionnaire.setOnClickListener {
            val operationNo = etOperationNo.text.toString()
            val betPat = findViewById<RadioButton>(rgBetPat.checkedRadioButtonId)?.text.toString()
            val befAsept = findViewById<RadioButton>(rgBefAsept.checkedRadioButtonId)?.text.toString()
            val aftBF = findViewById<RadioButton>(rgAftBF.checkedRadioButtonId)?.text.toString()
            val aftPat = findViewById<RadioButton>(rgAftPat.checkedRadioButtonId)?.text.toString()
            val aftPSurr = findViewById<RadioButton>(rgAftPSurr.checkedRadioButtonId)?.text.toString()
            val gloves = findViewById<RadioButton>(rgGloves.checkedRadioButtonId)?.text.toString()

            val questionnaire = QuestionnaireData(operationNo, betPat, befAsept, aftBF, aftPat, aftPSurr, gloves)
            val ref = database.reference.child("entries").child(entryId!!).child("staffEntries").child(staffEntryId!!).child("observations").push()
            ref.setValue(questionnaire).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (currentObservation < numObservations) {
                        val intent = Intent(this, QuestionnaireActivity::class.java)
                        intent.putExtra("entryId", entryId)
                        intent.putExtra("staffEntryId", staffEntryId)
                        intent.putExtra("numObservations", numObservations)
                        intent.putExtra("currentObservation", currentObservation + 1)
                        intent.putExtra("numStaffEntries", numStaffEntries)
                        intent.putExtra("currentStaffEntry", currentStaffEntry)
                        startActivity(intent)
                    } else if (currentStaffEntry < numStaffEntries) {
                        val intent = Intent(this, FormActivity::class.java)
                        intent.putExtra("entryId", entryId)
                        intent.putExtra("numStaffEntries", numStaffEntries)
                        intent.putExtra("currentStaffEntry", currentStaffEntry + 1)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "$numStaffEntries staff entries done!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                } else {
                    Toast.makeText(this, "Entry Not Done!", Toast.LENGTH_SHORT).show()
                    // Handle the error
                }
            }
        }
    }

    override fun onBackPressed() {

        // Show a toast message instead of navigating back
        Toast.makeText(this, "Please fill in the details!!", Toast.LENGTH_SHORT).show()

        // Uncomment the line below if want to do back navigation completely
        // super.onBackPressed()
    }
}
