package com.example.bookintroapp.view.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.fragment.base.BaseFragment
import com.example.bookintroapp.view.viewmodel.user.ChangePasswdViewModel

// パスワード変更フラグメント
class ChangePasswdFragment : BaseFragment() {

    // ビューモデル
    private lateinit var passwdViewModel : ChangePasswdViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO 初回起動

        // ビューモデル設定
        passwdViewModel = ViewModelProvider(this).get(ChangePasswdViewModel::class.java)
        passwdViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_chpasswd_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 初期化後の処理
        passwdViewModel.setView(view, this)
    }
}