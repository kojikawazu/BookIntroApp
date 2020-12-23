package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.book.BookDetailModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// 書籍詳細ビューモデル
class BookDetailViewModel : BaseViewModel() {

    // モデル
    private lateinit var bookDetailModel: BookDetailModel

    override fun initModel() {
        // TODO モデルの初期化
        bookDetailModel = BookDetailModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        bookDetailModel.setLayout(view)
        bookDetailModel.setListener(view, frag)
    }
}