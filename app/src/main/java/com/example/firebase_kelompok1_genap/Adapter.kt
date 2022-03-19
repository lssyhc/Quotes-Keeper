package com.example.firebase_kelompok1_genap

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.`AppCompatMultiAutoCompleteTextView$InspectionCompanion`
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Quotes>) :
    ArrayAdapter<Quotes>(mCtx, layoutResId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textNameQuotes = view.findViewById<TextView>(R.id.textNameQuotes)
        val textQuoteQuotes = view.findViewById<TextView>(R.id.textQuoteQuotes)

        val iconEdit = view.findViewById<ImageView>(R.id.icon_edit)
        val iconDelete = view.findViewById<ImageView>(R.id.icon_delete)

        val quote = list[position]

        textNameQuotes.text = quote.name
        textQuoteQuotes.text = quote.quote

        iconEdit.setOnClickListener {
            showUpdateDialog(quote)
        }
        iconDelete.setOnClickListener {
            Deleteinfo(quote)
        }

        return view
    }

    private fun showUpdateDialog(quote: Quotes) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textNameUpdate = view.findViewById<EditText>(R.id.inputNameUpdate)
        val textQuoteUpdate = view.findViewById<EditText>(R.id.inputQuoteUpdate)

        textNameUpdate.setText(quote.name)
        textQuoteUpdate.setText(quote.quote)

        builder.setView(view)

        builder.setPositiveButton("Ubah") { dialog, which ->

            val dbQuotes = FirebaseDatabase.getInstance().getReference("QUOTES")

            val name = textNameUpdate.text.toString().trim()

            val kutipan = textQuoteUpdate.text.toString().trim()

            if (name.isEmpty()) {
                textNameUpdate.error = "Tolong masukkan nama"
                textNameUpdate.requestFocus()
                return@setPositiveButton
            }

            if (kutipan.isEmpty()) {
                textQuoteUpdate.error = "Tolong masukkan quote"
                textQuoteUpdate.requestFocus()
                return@setPositiveButton
            }

            val quoteQuote = Quotes(quote.id, name, kutipan)

            dbQuotes.child(quote.id).setValue(quoteQuote).addOnCompleteListener {
                Toast.makeText(mCtx, "Terupdate!!", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("Batal") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

    private fun Deleteinfo(quote: Quotes) {
        val progressDialog = ProgressDialog(context, com.google.android.material.R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Menghapus...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("QUOTES")
        mydatabase.child(quote.id).removeValue()
        Toast.makeText(mCtx, "Terhapus!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ShowActivity::class.java)
        context.startActivity(intent)
    }
}