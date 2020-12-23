package com.example.bookintroapp.view.viewmodel.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.book.BookMyPageModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// マイページビューモデル
class BookMypageViewModel : BaseViewModel() {

    // モデル
    private lateinit var mypageModel: BookMyPageModel

    override fun initModel() {
        // TODO モデルの初期化
        mypageModel = BookMyPageModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        mypageModel.setLayout(view)
        mypageModel.setListener(view, frag)
    }
}