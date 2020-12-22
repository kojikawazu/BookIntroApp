package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.BookAddForm
import java.sql.Timestamp
import java.util.*

// 書籍エンティティ
class BookEntity() {

    init{
        // TODO 初期化
    }

    constructor(bookId: String, userId: String, bookName: String, bookTitle: String,
                satisCnt: Int, niceCnt: Int, markCnt: Int, replyCnt: Int, comment: String, created: Date) : this(){
        // TODO コンストラクタ
        BookId = bookId
        UserId = userId
        BookName = bookName
        BookTitle = bookTitle
        SatisCnt = satisCnt
        NiceCnt = niceCnt
        MarkCnt = markCnt
        ReplyCnt = replyCnt
        Comment = comment
        Created = created
    }

    constructor(userEntity: UserEntity, bookAddForm: BookAddForm,date: Date) : this() {
        // TODO コンストラクタ
        BookId = "0"
        UserId = userEntity.UserId
        BookName =  bookAddForm.BookNameString
        BookTitle = bookAddForm.TitleString
        SatisCnt = 1
        NiceCnt = 0
        MarkCnt = 0
        ReplyCnt = 0
        Comment = bookAddForm.CommentString
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
    var MarkCnt : Int = 0
        get(){
            return field
        }
        private set
    var MarkCntDisplay: String = ""
        get(){
            return MarkCnt?.toString()
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
    var ReplyCnt : Int = 0
        get(){
            return field
        }
        private set
    var ReplyCntDisplay : String = ""
        get(){
            return ReplyCnt.toString()
        }
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

    fun setNiceCnt(cnt: Int){
        // TODO いいね数インクリメント
        NiceCnt = cnt
    }

    fun setMarkCnt(cnt: Int){
        // TODO ブックマークリストインクリメント
        MarkCnt = cnt
    }

}