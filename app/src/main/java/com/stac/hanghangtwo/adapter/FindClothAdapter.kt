package com.stac.hanghangtwo.adapter

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stac.hanghangtwo.Entity.ImageUploadInfo
import com.stac.hanghangtwo.R
import com.stac.hanghangtwo.util.Id
import java.util.*

class FindClothAdapter (
        val context : Context,
        val items : List<ImageUploadInfo>
) : RecyclerView.Adapter<FindClothAdapter.ViewHolder>() {

    var countIsWearFalse = 0

    override fun getItemCount(): Int {
        return items.filter {
            it.imageSign
        }.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(!items[position].imageSign) countIsWearFalse++
        holder.bind(items[position + countIsWearFalse])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_clothlist,parent,false))
    }

    inner class ViewHolder (val v : View) : RecyclerView.ViewHolder(v) {
        val clothName : TextView by lazy { v.findViewById<TextView>(R.id.item_cloth_name) }
        val clothImage : ImageView by lazy { v.findViewById<ImageView>(R.id.item_cloth_image) }
        val clothBackground : ConstraintLayout by lazy { v.findViewById<ConstraintLayout>(R.id.item_cloth_background)}

        fun bind(info : ImageUploadInfo) {
            clothName.text = info.imageName
            Glide.with(this@FindClothAdapter.context).load(info.imageURL).override(170,170).into(clothImage)
            clothBackground.setOnClickListener {
                it.setBackgroundColor(ContextCompat.getColor(v.context,R.color.findSelect))
                clothName.setTextColor(Color.WHITE)
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val bluetoothSocket = bluetoothAdapter
                        .bondedDevices
                        .filter { it.name=="HANGHANG" }
                        .get(0)
                        .createRfcommSocketToServiceRecord(UUID.fromString(Id.uuid))
                bluetoothSocket.connect()
                communicationBluetooth(bluetoothSocket)

                bluetoothSocket.close()
            }
        }
        fun communicationBluetooth(socket : BluetoothSocket) {
            val inputStream = socket.inputStream
            val outputStream = socket.outputStream
        }
    }
}