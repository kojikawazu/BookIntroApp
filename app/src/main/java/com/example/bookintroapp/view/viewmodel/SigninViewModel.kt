package com.example.bookintroapp.view.viewmodel

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.SigninModel

class SigninViewModel : ViewModel(){

    private lateinit var _model: SigninModel

    fun initModel(){
        _model = SigninModel()
    }

    fun setView(view: View, frag: Fragment){
        _model.setLayout(view)
        _model.setListener(view, frag)
    }

    /*
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    */

}