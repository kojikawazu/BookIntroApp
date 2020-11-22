package com.example.bookintroapp.form

import android.widget.EditText
import com.example.bookintroapp.helper.DomainHelper

class SigninForm() {

    init{

    }

    constructor(emailEdit: EditText, passwdEdit: EditText) : this(){
        // TODO コンストラクタ
        EmailEdit = emailEdit
        PasswdEdit = passwdEdit
    }

    private var EmailEdit : EditText? = null
        get(){
            return field
        }
    private var PasswdEdit : EditText? = null
        get(){
            return field
        }
    val EmailString: String
        get(){
            if( EmailEdit != null ){    return EmailEdit!!.text.toString() }
            else{                       return ""                   }
        }
    val PasswdString: String
        get(){
            if( PasswdEdit != null ){    return PasswdEdit!!.text.toString() }
            else{                       return ""                   }
        }

    fun isEmpty(): Boolean {
        // TODO 空チェック
        return ( EmailEdit?.text.toString().isEmpty() || EmailEdit?.text.toString().isEmpty() );
    }

    fun checkEmail() : Boolean{
        // TODO メール形式チェック
        if(EmailEdit != null){
            return DomainHelper.IsEmailMatch(EmailEdit!!.text.toString())
        }
        return false
    }

}