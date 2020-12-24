package com.example.bookintroapp.valueobject.form

import android.widget.Button
import android.widget.TextView
import com.example.bookintroapp.repository.FollowRepository
import com.example.bookintroapp.repository.IFollowRepository
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.viewtext.FollowText
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

// マイページフォーム
class BookMyPageForm() {

    constructor(followView: TextView, followerView: TextView, followButton: Button) : this(){
        // TODO コンストラクタ
        Follow = FollowText(followView, followerView)
        FollowButton = followButton
    }

    // getter
    var Follow: FollowText? = null
        get(){
            return field
        }
        private set
    private var FollowButton : Button? = null
        get(){
            return field
        }

}