package com.example.bookintroapp.view.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.BookMarkModel

// ブックマークビューモデル
class BookMarkViewModel : BaseViewModel() {

    // モデル
    private lateinit var bookMarkModel: BookMarkModel

    override fun initModel() {
        // TODO モデルの初期化
        bookMarkModel = BookMarkModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        bookMarkModel.setLayout(view)
        bookMarkModel.setListener(view, frag)
    }

}