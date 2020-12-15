package com.example.bookintroapp.model

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

// 書籍メインモデル
class BookMainModel : ModelBase() {

    override fun setLayout(view: View) {
        // TODO レイアウト設定
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO リスナー設定

        // ブックリスナー
        val ac : MainActivity = frag.activity as MainActivity
        ac.setBookListener(frag)

    }
}