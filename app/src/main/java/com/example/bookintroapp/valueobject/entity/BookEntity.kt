package com.example.bookintroapp.valueobject.entity

import java.sql.Timestamp
import java.util.*

class BookEntity() {

    init{
        // TODO 初期化
    }

    constructor(bookId: String, userId: String, bookName: String, bookTitle: String, satisCnt: Int, niceCnt: Int, comment: String, created: Date) : this(){
        // TODO コンストラクタ
        BookId = bookId
        UserId = userId
        BookName = bookName
        BookTitle = bookTitle
        SatisCnt = satisCnt
        NiceCnt = niceCnt
        Comment = comment
        Created = created
    }

    var BookId : String = ""
        get(){
            return field
        }
        private set
    var UserId : String = ""
        get(){
            return field
        }
        private set
    var BookName : String = ""
        get(){
            return field
        }
        private set
    var BookTitle : String = ""
        get(){
            return field
        }
        private set
    var SatisCnt : Int = 0
        get(){
            return field
        }
        private set
    var SatisCntDisplay: String = ""
        get(){
            return SatisCnt?.toString()
        }
        private set
    var NiceCnt : Int = 0
        get(){
            return field
        }
        private set
    var NiceCntDisplay : String = ""
        get(){
            return NiceCnt?.toString()
        }
        private set
    var Comment : String = ""
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