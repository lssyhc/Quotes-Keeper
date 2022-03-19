package com.example.firebase_kelompok1_genap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("QUOTES")

        btnTulis.setOnClickListener {
            savedata()
            val intent = Intent(this, ShowActivity::class.java)
            startActivity(intent)
        }

        btnList.setOnClickListener {
            val intent = Intent(this, ShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        val name = inputName.text.toString()
        val kutipan = inputQuote.text.toString()

        val quoteId = ref.push().key.toString()
        val quote = Quotes(quoteId, name, kutipan)

        ref.child(quoteId).setValue(quote).addOnCompleteListener {
            Toast.makeText(this, "Sukses!!", Toast.LENGTH_SHORT).show()
            inputName.setText("")
            inputQuote.setText("")
        }
    }
}