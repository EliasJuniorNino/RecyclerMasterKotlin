package com.eliasjuniornino.recyclermasterkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eliasjuniornino.recyclermasterkotlin.model.email
import com.eliasjuniornino.recyclermasterkotlin.model.fakeEmails
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mooveit.library.Fakeit
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: EmailAdapter
    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fakeit.init()

        adapter = EmailAdapter(fakeEmails())
        recyclerViewMain = findViewById(R.id.recycler_view_main)
        fab = findViewById(R.id.fab)

        recyclerViewMain.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
        fab.let {
            it.setOnClickListener {
                addEmail()
                adapter.notifyItemInserted(0)
                recyclerViewMain.scrollToPosition(0)
            }
        }
    }

    private fun addEmail() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).parse(
            Fakeit.dateTime().dateFormatter()
        )
        adapter.emails.add(0, email {
            stared = false
            unread = true
            user = Fakeit.name().firstName()
            subject = Fakeit.company().name()
            date = SimpleDateFormat("d MMM", Locale("pt", "BR")).format(sdf)
            preview = mutableListOf<String>().apply {
                repeat(10) {
                    add(Fakeit.lorem().words())
                }
            }.joinToString(" ")
        })
    }
}