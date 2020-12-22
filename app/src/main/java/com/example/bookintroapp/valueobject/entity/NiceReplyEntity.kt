package com.example.bookintroapp.valueobject.entity

import com.google.firebase.firestore.auth.User
import java.util.*

// リプライいいねエンティティ
class NiceReplyEntity() {

    init{
        // TODO 初期化
    }

    constructor(niceRId: String, replyId: String, userId: String, created: Date) : this(){
        // TODO コンストラクタ
        NiceRId = niceRId
        ReplyId = replyId
        UserId = userId
        Created = created
    }

    constructor(replyEntity: ReplyEntity, userEntity: UserEntity, created: Date) : this(){
        // TODO コンストラクタ
        NiceRId = "0"
        ReplyId = replyEntity.ReplyId
        UserId = userEntity.UserId
        Created = created
    }

    var NiceRId : String = ""
        get(){
            return field
        }
        private set
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
    var Created : Date = Date()
        get(){
            return field
        }
        private set
}