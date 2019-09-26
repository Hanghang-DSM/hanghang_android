package com.stac.hanghangtwo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stac.hanghangtwo.Entity.ImageUploadInfo
import com.stac.hanghangtwo.R

class HangClothAdapter(val context : Context, val items : List<ImageUploadInfo>) : RecyclerView.Adapter<HangClothAdapter.ViewHolder>() {

    var countDiscardList = 0

    override fun getItemCount() = items.filter { !it.imageSign }.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items[position].imageSign) countDiscardList++
        holder.bind(items[position + countDiscardList])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clothlist,parent,false))
    }

    class ViewHolder(val v : View) : RecyclerView.ViewHolder(v) {
        val clothName = v.findViewById<TextView>(R.id.item_cloth_name)
        val clothImage = v.findViewById<ImageView>(R.id.item_cloth_image)
        val background = v.findViewById<ConstraintLayout>(R.id.item_cloth_background)
        fun bind(item : ImageUploadInfo) {
            clothName.text = item.imageName
            Glide.with(v).load(item.imageURL).override(170,170).into(clothImage)
            background.setOnClickListener {

            }
        }
    }
}