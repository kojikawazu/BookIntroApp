package com.example.bookintroapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ControllerLoader
import com.example.bookintroapp.viewmodel.SigninModel

// class サインイン
class MainActivity : AppCompatActivity() {

    // ビューモデル
    private var _model : SigninModel

    init{
        // TODO 初期化
        _model = SigninModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // レイアウトのバインド
        Create_layout()
    }

    private fun Create_layout(){
        // TODO レイアウトのバインド
        _model.setLayout(this)
        _model.setListener(this)
    }
}