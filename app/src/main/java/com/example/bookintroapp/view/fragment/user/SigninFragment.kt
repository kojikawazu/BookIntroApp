package com.example.bookintroapp.view.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.fragment.base.BaseFragment
import com.example.bookintroapp.view.viewmodel.user.SigninViewModel

// サインインフラグメント
class SigninFragment : BaseFragment() {

    // ビューモデル
    private lateinit var signinViewModel: SigninViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO 初回生成

        // ビューモデル設定
        signinViewModel = ViewModelProvider(this).get(SigninViewModel::class.java)
        signinViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_signin_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 生成した後の処理
        signinViewModel.setView(view, this)

    }
}