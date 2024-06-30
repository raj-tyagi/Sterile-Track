package com.example.hospitalapp

import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TableActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: FirebaseDatabase
    private var serialNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        tableLayout = findViewById(R.id.tableLayout)
        database = FirebaseDatabase.getInstance()

        fetchAndDisplayData()
    }

    private fun fetchAndDisplayData() {
        val ref = database.reference.child("entries")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (entry in snapshot.children) {
                    for (staffEntry in entry.child("staffEntries").children) {
                        val icuName = staffEntry.child("icuNumber").getValue(String::class.java) ?: ""
                        val staffName = staffEntry.child("staffName").getValue(String::class.java) ?: ""
                        val staffPost = staffEntry.child("staffPost").getValue(String::class.java) ?: ""
                        val entryTime = staffEntry.child("entryTime").getValue(String::class.java) ?: ""
                        val purpose = staffEntry.child("purpose").getValue(String::class.java) ?: ""

                        for (observation in staffEntry.child("observations").children) {
                            serialNumber++
                            val operationNo = observation.child("operationNo").getValue(String::class.java) ?: ""
                            val betPat = observation.child("betPat").getValue(String::class.java) ?: ""
                            val befAsept = observation.child("befAsept").getValue(String::class.java) ?: ""
                            val aftBF = observation.child("aftBF").getValue(String::class.java) ?: ""
                            val aftPat = observation.child("aftPat").getValue(String::class.java) ?: ""
                            val aftPSurr = observation.child("aftPSurr").getValue(String::class.java) ?: ""

                            val tableRow = TableRow(this@TableActivity)
                            tableRow.addView(createTextView(serialNumber.toString()))
                            tableRow.addView(createTextView(icuName))
                            tableRow.addView(createTextView(staffName))
                            tableRow.addView(createTextView(staffPost))
                            tableRow.addView(createTextView(entryTime))
                            tableRow.addView(createTextView(purpose))
                            tableRow.addView(createTextView(operationNo))
                            tableRow.addView(createTextView(betPat))
                            tableRow.addView(createTextView(befAsept))
                            tableRow.addView(createTextView(aftBF))
                            tableRow.addView(createTextView(aftPat))
                            tableRow.addView(createTextView(aftPSurr))

                            tableLayout.addView(tableRow)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error to the console for debugging purposes
                Log.e("DatabaseError", "Error: ${error.message}")

                // Display a toast message to inform the user about the error
                Toast.makeText(this@TableActivity, "Failed to retrieve data: ${error.message}", Toast.LENGTH_LONG).show()

                // Optionally, you can handle specific error codes
                when (error.code) {
                    DatabaseError.DISCONNECTED -> {
                        // Handle the case where the connection was lost
                        Log.e("DatabaseError", "The connection to the database was lost.")
                    }
                    DatabaseError.NETWORK_ERROR -> {
                        // Handle network issues
                        Log.e("DatabaseError", "A network error occurred.")
                    }
                    DatabaseError.PERMISSION_DENIED -> {
                        // Handle permission issues
                        Log.e("DatabaseError", "Permission denied to access the database.")
                    }
                    else -> {
                        // Handle other errors
                        Log.e("DatabaseError", "An unknown error occurred.")
                    }
                }
            }

        })
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.textSize = 16f
        textView.setPadding(16, 16, 16, 16)
        textView.gravity = android.view.Gravity.CENTER
        textView.setTextColor(android.graphics.Color.WHITE) // Set text color to white
        return textView
    }
}
