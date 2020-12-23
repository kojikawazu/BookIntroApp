package com.example.bookintroapp.view.viewmodel.user

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.user.SignupModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

// サインアップビューモデル
class SignupViewModel : BaseViewModel(){

    // モデル
    private lateinit var _model : SignupModel

    override fun initModel() {
        // TODO モデルの初期化
        _model = SignupModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        _model.setLayout(view)
        _model.setListener(view, frag)
    }
}