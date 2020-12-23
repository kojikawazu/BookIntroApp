package com.example.bookintroapp.view.fragment.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.fragment.base.BaseFragment
import com.example.bookintroapp.view.viewmodel.book.BookMypageViewModel

// マイページフラグメント
class BookMypageFragment : BaseFragment() {

    // ビューモデル
    private lateinit var bookMypageViewModel: BookMypageViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // TODO 初回起動

        // ビューモデル設定
        bookMypageViewModel = ViewModelProvider(this).get(BookMypageViewModel::class.java)
        bookMypageViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_bookmypage_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO 初期化後の処理
        bookMypageViewModel.setView(view, this)



    }
}