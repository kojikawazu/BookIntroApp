package com.example.bookintroapp.view.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bookintroapp.R
import com.example.bookintroapp.view.fragment.base.BaseFragment
import com.example.bookintroapp.view.viewmodel.user.SignupViewModel

// サインアウトフラグメント
class SignupFragment : BaseFragment() {

    // ビューモデル
    private lateinit var signupViewModel : SignupViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // TODO 初回起動

        // ビューモデル設定
        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        signupViewModel.initModel()

        // フラグメント設定
        val root = setInflate(R.layout.fragment_signup_layout, inflater, container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 初期化後の処理
        signupViewModel.setView(view, this)
    }


}