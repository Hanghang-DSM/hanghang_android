package com.stac.hanghangtwo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.stac.hanghangtwo.Entity.ImageUploadInfo

import com.stac.hanghangtwo.R
import com.stac.hanghangtwo.adapter.HangClothAdapter
import com.stac.hanghangtwo.databinding.ActivityHangBinding

class HangActivity : BaseActivity<ActivityHangBinding>(R.layout.activity_hang) {

    val items = ArrayList<ImageUploadInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.hangRecyclerview.adapter = HangClothAdapter(this,items)
        binding.hangRecyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


    }
}
