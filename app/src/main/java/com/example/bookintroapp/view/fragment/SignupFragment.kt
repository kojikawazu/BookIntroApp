package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.model.SignupModel
import com.example.bookintroapp.view.viewmodel.SigninViewModel
import com.example.bookintroapp.view.viewmodel.SignupViewModel

// サインアウトフラグメント
class SignupFragment : Fragment() {

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
        val root = inflater.inflate(R.layout.fragment_signup_layout, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // TODO 初期化後の処理
        signupViewModel.setView(view, this)
    }


}