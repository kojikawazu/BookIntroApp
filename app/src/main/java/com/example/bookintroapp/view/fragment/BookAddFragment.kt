package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.viewmodel.BookAddViewModel
import com.example.bookintroapp.view.viewmodel.BookMainViewModel

// 書籍追加フラグメント
class BookAddFragment : Fragment() {

    // ビューモデル
    private lateinit var bookAddViewModel: BookAddViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO 初回起動

        // ビューモデル設定
        bookAddViewModel = ViewModelProvider(this).get(BookAddViewModel::class.java)
        bookAddViewModel.initModel()

        // フラグメント設定
        val root = inflater.inflate(R.layout.fragment_bookadd_layout, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 初期化後の処理
        bookAddViewModel.setView(view, this)
    }
}