package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.bookform.BookAddModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

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