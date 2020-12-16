package com.example.bookintroapp.valueobject.entity

import java.util.*

class NiceEntity() {

    init{
        // TODO 初期化
    }

    constructor(niceId: String, userId: String, bookId: String, created: Date) : this(){
        // TODO コンストラクタ
        NiceId = niceId
        UserId = userId
        BookId = bookId
        Created = created
    }

    var NiceId : String = ""
        get(){
            return field
        }
        private set
    var UserId : String = ""
        get(){
            return field
        }
        private set
    var BookId : String = ""
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