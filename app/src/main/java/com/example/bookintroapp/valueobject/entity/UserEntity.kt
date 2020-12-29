package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.form.SignupForm
import java.util.*

// ユーザエンティティ
class UserEntity() {

    init{
        // TODO 初期化
    }

    constructor(userId: String, userName: String, email: String, forgotPasswd: String, created: Date) : this(){
        // TODO コンストラクタ
        UserId = userId
        UserName = userName
        Email = email
        ForgotPasswd = forgotPasswd
        Created = created
    }

    constructor(signupForm: SignupForm, date: Date) : this(){
        // TODO コンストラクタ
        UserId = "0"
        UserName = signupForm!!.UserNameString
        Email = signupForm!!.EmailString
        ForgotPasswd = signupForm!!.PasswdForgotString
        Created = date
    }

    var UserId : String = ""
        get(){
            return field
        }
        private set
    var UserName : String = ""
        get(){
            return field
        }
        private set
    var Email : String = ""
        get(){
            return field
        }
        private set
    var ForgotPasswd : String = ""
        get(){
            return field
        }
        private set
    var Created : Date = Date()
        get(){
            return field
        }
        private set


}