package com.example.bookintroapp.valueobject.form.book

import android.util.Log
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

    // リポジトリ
    private val _followRepository: IFollowRepository = FollowRepository()

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
    var FollowButton : Button? = null
        get(){
            return field
        }
        private set

    fun updateButtonUI(userEntity: UserEntity, followEntity: UserEntity){
        // TODO フォローボタンの更新

        // フォロー個数を取得
        val tsk: Task<QuerySnapshot> = _followRepository.select_byuserId_followerId(userEntity.UserId, followEntity.UserId)
        _followRepository.execing(tsk)
        if(_followRepository.isSuccessed(tsk)){
            val cnt: Int = _followRepository.getResultEntiryCount(tsk)
            FollowButton?.isEnabled = (cnt == 0)
        }
        else{
            FollowButton?.isEnabled = false
        }
    }

}