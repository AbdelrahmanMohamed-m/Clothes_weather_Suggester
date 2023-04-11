package com.example.api_example.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.api_example.R
import com.example.api_example.databinding.ClothesItemBinding

class clothesAdapter(private val items: List<Clothes>) :
    RecyclerView.Adapter<clothesAdapter.ClothesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        val viewBinder =
            LayoutInflater.from(parent.context).inflate(R.layout.clothes_item, parent, false)
        return ClothesViewHolder(viewBinder)
    }

    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        return holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size

    }

    class ClothesViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val clothesItemBinding = ClothesItemBinding.bind(viewItem)
        fun bind(clothes: Clothes) {
            clothesItemBinding.clothesImage.setImageResource(clothes.image)
        }
    }
}


