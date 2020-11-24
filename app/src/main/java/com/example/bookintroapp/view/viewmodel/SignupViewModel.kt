package com.example.bookintroapp.view.viewmodel

import android.view.FrameMetrics
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.SignupModel

// サインアップビューモデル
class SignupViewModel : ViewModel(){

    // サインアップ
    private lateinit var _model : SignupModel

    fun initModel() {
        // TODO モデルの初期化
        _model = SignupModel()
    }

    fun setView(view: View, frag: Fragment) {
        // TODO ビューの設定
        _model.setLayout(view)
        _model.setListener(view, frag)
    }
}