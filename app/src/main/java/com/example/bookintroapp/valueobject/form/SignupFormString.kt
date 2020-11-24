package com.example.bookintroapp.valueobject.form

class SignupFormString() {

    init{
        // TODO 初期化

    }

    constructor(userName : String, email : String, passwdNew : String, passwdForgot : String) : this(){
        // TODO コンストラクタ
        this.UserName = userName
        this.Email = email
        this.PasswdNew = passwdNew
        this.PasswdForgot = passwdForgot
    }


    var UserName : String = ""
        get(){
            return field
        }
    var Email : String = ""
        get(){
            return field
        }
    var PasswdNew : String = ""
        get(){
            return field
        }
    var PasswdForgot : String = ""
        get(){
            return field
        }
}