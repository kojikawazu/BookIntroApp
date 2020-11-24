package com.example.bookintroapp.valueobject.entity

import java.util.*

class UserEntity() {

    init{

    }

    constructor(userId: String, userName: String, email: String, forgotPasswd: String, created: Date) : this(){
        // TODO コンストラクタ
        UserId = userId
        UserName = userName
        Email = email
        ForgotPasswd = forgotPasswd
        Created = created
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