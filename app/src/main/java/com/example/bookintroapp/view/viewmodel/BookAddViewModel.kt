package com.example.bookintroapp.view.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.BookAddModel
import com.example.bookintroapp.model.BookMyPageModel

// 書籍追加ビューモデル
class BookAddViewModel : BaseViewModel() {

    // モデル
    private lateinit var bookAddModel: BookAddModel

    override fun initModel() {
        // TODO モデルの初期化
        bookAddModel = BookAddModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        bookAddModel.setLayout(view)
        bookAddModel.setListener(view, frag)
    }

}