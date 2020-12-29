package com.example.bookintroapp.valueobject.form.form

import android.widget.Button
import android.widget.EditText
import com.example.bookintroapp.helper.DomainHelper

// サインアップフォーム
class SignupForm() {

    init{
        // TODO 初期化
    }

    constructor(userNameEditText: EditText, emailEditText: EditText,
                passwdNewEditText: EditText, passwdOneEditText: EditText,
                passwdForgotEditText: EditText, signupButton: Button) :
                this(){
        // TODO コンストラクタ
        UserNameEdit = userNameEditText
        EmailEdit = emailEditText
        PasswdNewEdit = passwdNewEditText
        PasswdOneEdit = passwdOneEditText
        PasswdForgotEdit = passwdForgotEditText
        SignunButton = signupButton
    }

    // getter
    private var UserNameEdit : EditText? = null
        get(){
            return field
        }
    private var EmailEdit : EditText? = null
        get(){
            return field
        }
    private var PasswdNewEdit : EditText? = null
        get(){
            return field
        }
    private var PasswdOneEdit : EditText? = null
        get(){
            return field
        }
    private var PasswdForgotEdit :  EditText? = null
        get(){
            return field
        }
    private var SignunButton :  Button? = null
        get(){
            return field
        }
    val UserNameString : String
        get(){
            if(UserNameEdit != null){   return UserNameEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val EmailString : String
        get(){
            if(EmailEdit != null){   return EmailEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val PasswdNewString : String
        get(){
            if(PasswdNewEdit != null){   return PasswdNewEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val PasswdOneString : String
        get(){
            if(PasswdOneEdit != null){   return PasswdOneEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val PasswdForgotString : String
        get(){
            if(PasswdForgotEdit != null){   return PasswdForgotEdit!!.text.toString()  }
            else{                       return ""                       }
        }

    fun isEmpty(): Boolean {
        // TODO 空チェック
        return ( UserNameEdit?.text.toString().isEmpty() || EmailEdit?.text.toString().isEmpty() ||
                PasswdNewEdit?.text.toString().isEmpty() || PasswdForgotEdit?.text.toString().isEmpty() )
    }

    fun checkUserName(max : Int) : Boolean{
        // TODO ユーザチェック
        if(UserNameEdit != null){
            var len : Int = UserNameEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len, max) )
        }
        return false
    }

    fun checkEmail() : Boolean{
        // TODO メール形式チェック
        if(EmailEdit != null){
            return DomainHelper.IsEmailMatch(EmailEdit!!.text.toString())
        }
        return false
    }

    fun checkNewPasswd_len(max : Int) : Boolean{
        // TODO パスワードの長さ確認
        if(PasswdNewEdit != null && PasswdOneEdit != null){
            var len1 : Int = PasswdNewEdit!!.text.toString().length
            var len2 : Int = PasswdOneEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len1, max) && DomainHelper.IsRange(len2, max))
        }
        return false
    }

    fun checkNewPasswd_same() : Boolean{
        // TODO パスワードの一致確認
        if(PasswdNewEdit != null && PasswdOneEdit != null){
            return (DomainHelper.IsSame(PasswdNewEdit!!.text.toString(), PasswdOneEdit!!.text.toString()))
        }
        return false
    }

    fun checkForgotPasswd_len(max : Int) : Boolean{
        // TODO パスワードの長さ確認
        if(PasswdForgotEdit != null){
            var len : Int = PasswdForgotEdit!!.text.toString().length
            return (DomainHelper.IsRange(len, max))
        }
        return false
    }

    fun setOnClickListener(func: () -> Unit){
        // TODO タップリスナー
        SignunButton?.apply{
            setOnClickListener{
                func()
            }
        }
    }

}