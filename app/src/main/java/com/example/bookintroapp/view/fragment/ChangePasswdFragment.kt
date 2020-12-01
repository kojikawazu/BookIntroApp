package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.viewmodel.ChangePasswdViewModel
import com.example.bookintroapp.view.viewmodel.SignupViewModel

// パスワード変更フラグメント
class ChangePasswdFragment : Fragment() {

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
        val root = inflater.inflate(R.layout.fragment_chpasswd_layout, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 初期化後の処理
        passwdViewModel.setView(view, this)
    }
}