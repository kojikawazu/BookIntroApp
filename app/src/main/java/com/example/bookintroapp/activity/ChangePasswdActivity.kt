package com.example.bookintroapp.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.viewmodel.ChangePasswdModel

// class パスワード変更
class ChangePasswdActivity : AppCompatActivity() {

    private var _model : ChangePasswdModel

    init{
        _model = ChangePasswdModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.passwd_change_layout)
        create_layout()
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

    fun create_layout(){
        // TODO レイアウトの設定
        _model.setLayout(this)
        _model.setListener(this)

    }
}