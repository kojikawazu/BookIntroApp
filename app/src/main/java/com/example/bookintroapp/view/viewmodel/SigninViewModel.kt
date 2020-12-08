package com.example.bookintroapp.view.viewmodel

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookintroapp.model.SigninModel

class SigninViewModel : BaseViewModel(){

    private lateinit var _model: SigninModel

    override fun initModel(){
        _model = SigninModel()
    }

    override fun setView(view: View, frag: Fragment){
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