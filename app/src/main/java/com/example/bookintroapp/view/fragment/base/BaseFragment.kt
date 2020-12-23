package com.example.bookintroapp.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R

// フラグメント基底
abstract class BaseFragment : Fragment() {

    abstract override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    fun setInflate(id: Int, inflater: LayoutInflater, container: ViewGroup?): View? {
        // TODO フラグメント設定
        return inflater.inflate(id, container, false)
    }

}