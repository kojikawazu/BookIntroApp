package com.example.bookintroapp.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R
import com.example.bookintroapp.model.SignupModel

// class サインアップ
class SignupActivity : AppCompatActivity() {

    private var _model : SignupModel

    init{
        _model = SignupModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)
        create_layout()
    }

    private fun create_layout(){
        // TODO レイアウトの設定

        _model.setLayout(this)
        _model.setListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem?) : Boolean{
        // TODO 戻るボタンの処理
        when(item?.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}