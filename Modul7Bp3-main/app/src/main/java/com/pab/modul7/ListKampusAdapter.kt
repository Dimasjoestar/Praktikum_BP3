package com.pab.modul7

import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListKampusAdapter(private val listKampus: ArrayList<Kampus>) : RecyclerView.Adapter<ListKampusAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kampus, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kampus = listKampus[position]
        holder.imgPhoto.setImageResource(kampus.photo)
        holder.tvName.text = kampus.name
        holder.tvLokasi.text = kampus.lokasi
        holder.tvSejarah.text = kampus.sejarah

        holder.itemView.setOnClickListener { 
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_KAMPUS, kampus)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listKampus.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvLokasi: TextView = itemView.findViewById(R.id.tv_item_location)
        val tvSejarah: TextView = itemView.findViewById(R.id.tv_item_description)

        init {
            tvSejarah.movementMethod = ScrollingMovementMethod()
        }
    }
}