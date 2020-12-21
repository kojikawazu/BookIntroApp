package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.viewmodel.BookMypageViewModel
import com.example.bookintroapp.view.viewmodel.BookReplyViewModel

// リプライフラグメント
class BookReplyFragment : BaseFragment() {

    // ビューモデル
    private lateinit var bookReplyViewModel: BookReplyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // TODO 初回起動

        // ビューモデル設定
        bookReplyViewModel = ViewModelProvider(this).get(BookReplyViewModel::class.java)
        bookReplyViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_bookreply_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO 初期化後の処理
        bookReplyViewModel.setView(view, this)
    }
}