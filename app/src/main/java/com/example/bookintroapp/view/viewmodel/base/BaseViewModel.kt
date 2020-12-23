package com.example.bookintroapp.view.viewmodel.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

// ビューモデル基底
abstract class BaseViewModel : ViewModel() {

    // TODO モデルの初期化
    abstract fun initModel()
    // TODO ビューの設定
    abstract fun setView(view: View, frag: Fragment)
}