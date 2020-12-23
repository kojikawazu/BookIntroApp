package com.example.bookintroapp.valueobject.entity

import java.util.*

// フォローエンティティ
class FollowEntity() {

    constructor(id: String, userId: String, followerId: String, created: Date) : this(){
        // TODO コンストラクタ
        FollowId = id
        UserId = userId
        FollowerId = followerId
        Created = created
    }

    // getter
    var FollowId : String = ""
        get(){
            return field
        }
        private set
    var UserId : String = ""
        get(){
            return field
        }
        private set
    var FollowerId : String = ""
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