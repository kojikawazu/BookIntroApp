package com.example.bookintroapp.model.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class ModelBase {

    init{

    }

    // オーバーライド対象
    abstract fun setLayout(view: View)
    abstract fun setListener(view: View, frag: Fragment)

}