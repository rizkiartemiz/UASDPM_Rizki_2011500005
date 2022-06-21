package com.example.uasdpm_rizki_2011500005

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.uasdpm_rizki_2011500005.dbhelper

class EntryDataDosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_data_dosen)

        val modeEdit = intent.hasExtra("nidsn") && intent.hasExtra("nama") &&
                intent.hasExtra("jabtn") && intent.hasExtra("golpang") &&
                intent.hasExtra("pendidikantrkhr") && intent.hasExtra("bdgkeahlian") &&
                intent.hasExtra("prodi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdnidn = findViewById<EditText>(R.id.etKdnidn)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolpat = findViewById<Spinner>(R.id.spnGolpkt)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etAhli = findViewById<EditText>(R.id.etBidang)
        val etstudi = findViewById<EditText>(R.id.etProdi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etjabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val pangkat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@EntryDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            pangkat
        )
        spnGolpat.adapter = adpGolpat

        val adpJabatan = ArrayAdapter(
            this@EntryDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            etjabatan
        )
        spnJabatan.adapter = adpJabatan

        if(modeEdit) {
            val kodeNidn = intent.getStringExtra("nidn")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golpat = intent.getStringExtra("golpat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val studi = intent.getStringExtra("studi")

            etKdnidn.setText(kodeNidn)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(etjabatan.indexOf(jabatan))
            spnGolpat.setSelection(pangkat.indexOf(golpat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etAhli.setText(keahlian)
            etstudi.setText(studi)
        }
        etKdnidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etKdnidn.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty()
                && "${etAhli.text}".isNotEmpty() && "${etstudi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = dbhelper(this@EntryDataDosen)
                db.nidn = "${etKdnidn.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golonganpangkat = spnGolpat.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etAhli.text}"
                db.programstudi = "${etstudi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etKdnidn.text}")) {
                    Toast.makeText(
                        this@EntryDataDosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntryDataDosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntryDataDosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}