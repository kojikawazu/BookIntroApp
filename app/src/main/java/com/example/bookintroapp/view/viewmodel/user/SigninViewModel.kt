package com.example.bookintroapp.view.viewmodel.user

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.model.user.SigninModel
import com.example.bookintroapp.view.viewmodel.base.BaseViewModel

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