package com.example.bookintroapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ControllerLoader
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.viewmodel.SigninModel
import com.google.android.material.navigation.NavigationView

// class サインイン
class MainActivity : AppCompatActivity(){
//    class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // ビューモデル
    private val _model : SigninModel = SigninModel()

    init{
        // TODO 初期化
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO 起動
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setToolbar()

        // レイアウトのバインド
        Create_layout()
    }

    private fun setToolbar() {
        //setSupportActionBar(R.id.toolbar)
        //supportActionBar!!.setDisplayShowHomeEnabled(false)
    }

    private fun Create_layout(){
        // TODO レイアウトのバインド
        _model.setLayout(this)
        _model.setListener(this)
    }
}