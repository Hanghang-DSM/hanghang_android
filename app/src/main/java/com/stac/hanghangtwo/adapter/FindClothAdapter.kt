package com.stac.hanghangtwo.adapter

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stac.hanghangtwo.Entity.ImageUploadInfo
import com.stac.hanghangtwo.R
import com.stac.hanghangtwo.exception.BluetoothException
import com.stac.hanghangtwo.util.BluetoothThread
import com.stac.hanghangtwo.util.FIND
import com.stac.hanghangtwo.util.Id
import java.util.*
import kotlin.experimental.or

class FindClothAdapter(
        val items: List<ImageUploadInfo>
) : RecyclerView.Adapter<FindClothAdapter.ViewHolder>() {

    init {
        Id.id = (items.maxBy { it.imageId }?.imageId ?: Id.id)+1
    }
    
    var countDiscardList = 0
    override fun getItemCount() = items.filter { it.imageSign }.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!items[position].imageSign) countDiscardList++
        holder.bind(items,position + countDiscardList)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clothlist, parent, false))
    
    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val clothName: TextView by lazy { v.findViewById<TextView>(R.id.item_cloth_name) }
        val clothImage: ImageView by lazy { v.findViewById<ImageView>(R.id.item_cloth_image) }
        val clothBackground: ConstraintLayout by lazy { v.findViewById<ConstraintLayout>(R.id.item_cloth_background) }
    
        fun bind(infoList: List<ImageUploadInfo>, idx : Int) {
            val info = infoList[idx]
    
            clothName.text = info.imageName
            Glide.with(v).load(info.imageURL).override(170, 170).into(clothImage)
            clothBackground.setOnClickListener {
                it.setBackgroundColor(ContextCompat.getColor(v.context, R.color.findSelect))
                clothName.setTextColor(Color.WHITE)
                info.imageSign = false
                try {
                    val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                            ?: throw BluetoothException("Bluetooth를 지원하지 않거나 켜져있지 않습니다.")
                    val bluetoothSocket: BluetoothSocket? = bluetoothAdapter
                            .bondedDevices
                            .filter { it.name == "HANGHANG" }
                            .get(0)
                            .createRfcommSocketToServiceRecord(UUID.fromString(Id.uuid))
                    bluetoothSocket ?: throw BluetoothException("블루투스가 모듈에 연결되어있지 않습니다.")

                    communicationBluetooth(bluetoothSocket, info.imageId.toByte())
                    bluetoothSocket.close()                    BluetoothThread(bluetoothSocket,info.imageId.toByte()).start()
                    } catch (e: BluetoothException) {
                        Toast.makeText(v.context, e.msg, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(v.context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }