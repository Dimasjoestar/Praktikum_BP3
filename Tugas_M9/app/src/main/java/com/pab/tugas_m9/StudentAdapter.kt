package com.pab.tugas_m9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val items: List<Student>) : RecyclerView.Adapter<StudentAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.title)
        val meta: TextView = view.findViewById(R.id.source)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position]
        holder.name.text = s.nama ?: "-"
        val metaParts = mutableListOf<String>()
        s.nim?.let { metaParts.add(it) }
        s.nama_prodi?.let { metaParts.add(it) }
        holder.meta.text = metaParts.joinToString(" • ")
    }

    override fun getItemCount(): Int = items.size
}

