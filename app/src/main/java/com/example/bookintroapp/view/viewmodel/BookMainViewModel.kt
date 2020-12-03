package com.example.bookintroapp.view.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.BookMainModel
import com.example.bookintroapp.model.SigninModel
import com.example.bookintroapp.model.SignupModel

// 書籍紹介メインビューモデル
class BookMainViewModel  : ViewModel(){

    // モデル
    private lateinit var _model: BookMainModel

    fun initModel() {
        // TODO モデルの初期化
        _model = BookMainModel()
    }

    fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        _model.setLayout(view)
        _model.setListener(view, frag)
    }
}