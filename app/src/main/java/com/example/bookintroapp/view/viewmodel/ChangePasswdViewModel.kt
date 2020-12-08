package com.example.bookintroapp.view.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.ChangePasswdModel
import com.example.bookintroapp.model.SignupModel

// パスワード変更ビューモデル
class ChangePasswdViewModel: BaseViewModel() {

    // モデル
    private lateinit var _model : ChangePasswdModel

    override fun initModel() {
        // TODO モデルの初期化
        _model = ChangePasswdModel()
    }

    override fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        _model.setLayout(view)
        _model.setListener(view, frag)
    }
}