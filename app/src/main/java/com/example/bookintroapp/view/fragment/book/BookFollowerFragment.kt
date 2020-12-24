package com.example.bookintroapp.view.fragment.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.fragment.base.BaseFragment
import com.example.bookintroapp.view.viewmodel.book.BookDetailViewModel
import com.example.bookintroapp.view.viewmodel.book.BookFollowerViewModel

// フォロワーフラグメント
class BookFollowerFragment : BaseFragment() {

    // ビューモデル
    private lateinit var bookFollowerViewModel: BookFollowerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // TODO 初回起動

        // ビューモデル設定
        bookFollowerViewModel = ViewModelProvider(this).get(BookFollowerViewModel::class.java)
        bookFollowerViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_bookfollower_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO 初期化後の処理
        bookFollowerViewModel.setView(view, this)
    }
}