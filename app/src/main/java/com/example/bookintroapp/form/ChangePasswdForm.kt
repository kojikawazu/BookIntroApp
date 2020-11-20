package com.example.bookintroapp.form

import android.widget.EditText
import com.example.bookintroapp.helper.DomainHelper

class ChangePasswdForm() {

    init{

    }

    constructor(emailEdit : EditText, forgotEdit : EditText,
                newPasswdEdit : EditText, onePasswdEdit : EditText) : this(){
        // TODO コンストラクタ
        EmailEdit     = emailEdit
        ForgotEdit    = forgotEdit
        NewPasswdEdit = newPasswdEdit
        OnePasswdEdit = onePasswdEdit
    }

    // getter
    private var EmailEdit : EditText? = null
        get(){
            return field
        }
    private var ForgotEdit : EditText? = null
        get(){
            return field
        }
    private var NewPasswdEdit : EditText? = null
        get(){
            return field
        }
    private var OnePasswdEdit : EditText? = null
        get(){
            return field
        }

    fun isEmpty(): Boolean {
        // TODO 空チェック
        return ( EmailEdit?.text.toString().isEmpty() || ForgotEdit?.text.toString().isEmpty() ||
                NewPasswdEdit?.text.toString().isEmpty() || OnePasswdEdit?.text.toString().isEmpty() );
    }

    fun checkEmail() : Boolean{
        // TODO メール形式チェック
        if(EmailEdit != null){
            return DomainHelper.IsEmailMatch(EmailEdit!!.text.toString())
        }
        return false
    }

    fun checkForgotPasswd_len(max : Int) : Boolean{
        // TODO 忘れた用パスワードの長さ確認
        if(ForgotEdit != null){
            var len : Int = ForgotEdit!!.text.toString().length
            return (DomainHelper.IsRange(len, max))
        }
        return false
    }

    fun checkNewPasswd_len(max : Int) : Boolean{
        // TODO パスワードの長さ確認
        if(NewPasswdEdit != null && OnePasswdEdit != null){
            var len1 : Int = NewPasswdEdit!!.text.toString().length
            var len2 : Int = OnePasswdEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len1, max) && DomainHelper.IsRange(len2, max))
        }
        return false
    }

    fun checkNewPasswd_same() : Boolean{
        // TODO パスワードの一致確認
        if(NewPasswdEdit != null && OnePasswdEdit != null){
            return (DomainHelper.IsSame(NewPasswdEdit!!.text.toString(), OnePasswdEdit!!.text.toString()))
        }
        return false
    }

}