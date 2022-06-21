package com.example.uasdpm_rizki_2011500005

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.uasdpm_rizki_2011500005.dbhelper

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen: adapter
    private lateinit var DatDosen: ArrayList<campus>
    private lateinit var lvDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDosen = findViewById(R.id.lvDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        DatDosen = ArrayList()
        adpDosen = adapter(this@MainActivity, DatDosen)

        lvDosen.adapter =adpDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntryDataDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh() {
        val db = dbhelper(this@MainActivity)
        val data = db.tampil()
        repeat(DatDosen.size) { DatDosen.removeFirst() }
        if (data.count > 0) {
            while (data.moveToNext()) {
                val matkul = campus(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(matkul)
                adpDosen.notifyDataSetChanged()
            }
            lvDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}