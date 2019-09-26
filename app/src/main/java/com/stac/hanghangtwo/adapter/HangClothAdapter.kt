package com.stac.hanghangtwo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stac.hanghangtwo.Entity.ImageUploadInfo
import com.stac.hanghangtwo.R

class HangClothAdapter(val context : Context, val items : List<ImageUploadInfo>) : RecyclerView.Adapter<HangClothAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.filter { !it.imageSign }.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clothlist,parent,false))
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        fun bind(item : ImageUploadInfo) {

        }
    }
}