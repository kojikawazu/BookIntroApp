package com.example.bookintroapp.valueobject.entity

import java.util.*

// 返信エンティティ
class ReplyEntity() {

    init{
        // TODO 初期化
    }

    constructor(replyId: String, userId: String, bookId: String, comment: String,
                niceCnt: Int, satis: Int, created: Date) : this(){
        // TODO コンストラクタ
        ReplyId = replyId
        UserId = userId
        BookId = bookId
        Comment = comment
        NiceCnt = niceCnt
        Satis = satis
        Created = created
    }

    var ReplyId : String = ""
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
    var Comment : String = ""
        get(){
            return field
        }
        private set
    var NiceCnt : Int = 0
    get(){
        return field
    }
    private set
    var NiceCntDisplay : String = ""
        get(){
            return NiceCnt.toString()
        }
        private set
    var Satis : Int = 0
        get(){
            return field
        }
        private set
    var SatisDisplay : String = ""
        get(){
            return Satis.toString()
        }
        private set
    var Created : Date = Date()
        get(){
            return field
        }
        private set


}