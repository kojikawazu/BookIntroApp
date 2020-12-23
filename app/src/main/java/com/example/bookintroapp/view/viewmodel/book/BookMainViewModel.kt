package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.book.BookMainModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// 書籍紹介メインビューモデル
class BookMainViewModel  : BaseViewModel(){

    // モデル
    private lateinit var _model: BookMainModel

    override fun initModel() {
        // TODO モデルの初期化
        _model = BookMainModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        _model.setLayout(view)
        _model.setListener(view, frag)
    }
}