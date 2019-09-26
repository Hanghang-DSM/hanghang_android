package com.stac.hanghangtwo.util

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException

class BluetoothThread(val socket : BluetoothSocket, val sendData : Byte) : Thread(){
    override fun run() {
        socket.connect()
        try {
            socket.outputStream.write(byteArrayOf(sendData))
            socket.close()
        } catch (e : IOException) {
            Log.e("thread", e.message)
        }
    }
}