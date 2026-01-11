package com.pab.tugas_m9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val items: List<Article>) : RecyclerView.Adapter<NewsAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val source: TextView = view.findViewById(R.id.source)
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val a = items[position]
        holder.title.text = a.title ?: "-"
        holder.source.text = a.source ?: ""
        if (!a.urlToImage.isNullOrEmpty()) {
            Glide.with(holder.image.context)
                .load(a.urlToImage)
                .centerCrop()
                .into(holder.image)
        } else {
            holder.image.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int = items.size
}

