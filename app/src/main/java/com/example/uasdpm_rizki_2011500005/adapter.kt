package com.example.uasdpm_rizki_2011500005

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.uasdpm_rizki_2011500005.dbhelper

class adapter (
    private val getContext: Context,
    private val customListItem: ArrayList<campus>
): ArrayAdapter<campus>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvnidn = listLayout.findViewById(R.id.tvnidn)
                tvStudi = listLayout.findViewById(R.id.tvProdi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvnidn!!.setText(listItem.nidn)
        holder.tvStudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntryDataDosen::class.java)
            i.putExtra("nimdsn", listItem.nidn)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("jabtn", listItem.jabatan)
            i.putExtra("golpang", listItem.golonganpangkat)
            i.putExtra("pendidikantrkhr", listItem.pendidikan)
            i.putExtra("bdgkeahlian", listItem.keahlian)
            i.putExtra("prodi", listItem.programstudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = dbhelper(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvnidn!!.text
            val nama = holder.tvNmDosen!!.text
            val studi = holder.tvStudi!!.text

            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda Yakin Akan Menghapus Ini?
                                
                                $nama[$nidn - $studi]
                                """.trimIndent())
                setPositiveButton("Ya"){ _, _ ->
                    if(db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data Dosen Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvnidn: TextView? = null
        internal var tvStudi:TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null

    }
}