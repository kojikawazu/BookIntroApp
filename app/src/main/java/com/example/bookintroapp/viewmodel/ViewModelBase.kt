package com.example.bookintroapp.viewmodel

import androidx.appcompat.app.AppCompatActivity

abstract class ViewModelBase {

    init{

    }

    // オーバーライド対象
    abstract fun setLayout(activity : AppCompatActivity)
    abstract fun setListener(activity : AppCompatActivity)

}