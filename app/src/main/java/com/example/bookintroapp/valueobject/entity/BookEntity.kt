package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.BookAddForm
import java.sql.Timestamp
import java.util.*

// 書籍エンティティ
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

    constructor(userEntity: UserEntity, bookAddForm: BookAddForm,date: Date) : this() {
        // TODO コンストラクタ
        BookId = "0"
        UserId = userEntity!!.UserId
        BookName =  bookAddForm!!.BookNameString
        BookTitle = bookAddForm!!.TitleString
        SatisCnt = 0
        NiceCnt = 0
        Comment = bookAddForm!!.CommentString
        Created = date
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

    fun plus_niceCnt(){
        NiceCnt += 1
    }

}