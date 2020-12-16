package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.bookintroapp.R
import com.example.bookintroapp.view.viewmodel.BookAddViewModel
import com.example.bookintroapp.view.viewmodel.BookDetailViewModel
import com.example.bookintroapp.view.viewmodel.BookMypageViewModel
import io.grpc.NameResolver

// 書籍詳細フラグメント
class BookDetailFragment : BaseFragment(){

    // ビューモデル
    private lateinit var bookDetailViewModel: BookDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // TODO 初回起動

        // ビューモデル設定
        bookDetailViewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
        bookDetailViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_bookdetail_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO 初期化後の処理
        bookDetailViewModel.setView(view, this)
    }
}
