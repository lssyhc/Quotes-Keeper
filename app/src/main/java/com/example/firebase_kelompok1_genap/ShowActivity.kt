package com.example.firebase_kelompok1_genap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class ShowActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var list: MutableList<Quotes>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("QUOTES")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {

                    list.clear()
                    for (h in p0.children) {
                        val quote = h.getValue(Quotes::class.java)
                        list.add(quote!!)
                    }
                    val adapter = Adapter(this@ShowActivity, R.layout.quotes, list)
                    listView.adapter = adapter
                }
            }
        })
    }
}