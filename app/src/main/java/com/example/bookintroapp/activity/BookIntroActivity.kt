package com.example.bookintroapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R

class BookIntroActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_main_layout)

        // レイアウトのバインド
        Create_layout()
    }

    private fun Create_layout() {
        // TODO レイアウトの設定

    }
}