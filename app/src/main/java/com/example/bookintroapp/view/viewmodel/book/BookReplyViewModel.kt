package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.bookform.BookReplyModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// リプライビューモデル
class BookReplyViewModel : BaseViewModel() {

    // モデル
    private lateinit var replyModel: BookReplyModel

    override fun initModel() {
        // TODO モデルの初期化
        replyModel = BookReplyModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        replyModel.setLayout(view)
        replyModel.setListener(view, frag)
    }

}