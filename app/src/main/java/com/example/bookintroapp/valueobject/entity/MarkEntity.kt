package com.example.bookintroapp.valueobject.entity

import java.util.*

// ブックマークエンティティ
class MarkEntity() {

    init{
        // TODO 初期化
    }

    constructor(markId: String, userId: String, bookId: String, created: Date) : this(){
        // TODO コンストラクタ
        MarkId = markId
        UserId = userId
        BookId = bookId
        Created = created
    }

    var MarkId : String = ""
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