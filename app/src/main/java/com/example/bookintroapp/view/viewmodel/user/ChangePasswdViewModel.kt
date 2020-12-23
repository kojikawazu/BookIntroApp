package com.example.bookintroapp.view.viewmodel.user

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.user.ChangePasswdModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

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