package com.stac.hanghangtwo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity<T : ViewDataBinding>(val layoutId : Int) : AppCompatActivity() {
    val binding by lazy { DataBindingUtil.setContentView<T>(this,layoutId)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}