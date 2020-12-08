package com.example.bookintroapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R

abstract class BaseFragment : Fragment() {


    abstract override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    fun setInflate(id: Int, inflater: LayoutInflater, container: ViewGroup?): View? {
        return inflater.inflate(id, container, false)
    }

}