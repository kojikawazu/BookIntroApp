package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.book.BookDetailModel
import com.example.bookintroapp.model.book.BookFollowerModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// フォロワービューモデル
class BookFollowerViewModel : BaseViewModel() {

    // モデル
    private lateinit var bookFollowerModel: BookFollowerModel

    override fun initModel() {
        // TODO モデルの初期化
        bookFollowerModel = BookFollowerModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        bookFollowerModel.setLayout(view)
        bookFollowerModel.setListener(view, frag)
    }
}