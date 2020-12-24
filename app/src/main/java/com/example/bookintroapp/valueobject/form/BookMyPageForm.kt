package com.example.bookintroapp.valueobject.form

import android.widget.Button
import android.widget.TextView
import com.example.bookintroapp.repository.FollowRepository
import com.example.bookintroapp.repository.IFollowRepository
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

// マイページフォーム
class BookMyPageForm() {

    private val _followRepository: IFollowRepository = FollowRepository()

    constructor(followView: TextView, followerView: TextView, followButton: Button) : this(){
        // TODO コンストラクタ
        FollowView = followView
        FollowerView = followerView
        FollowButton = followButton
    }

    // getter
    private var FollowView : TextView? = null
        get(){
            return field
        }
    private var FollowerView : TextView? = null
        get(){
            return field
        }
    private var FollowButton : Button? = null
        get(){
            return field
        }

    fun updateFollowView(userEntity: UserEntity){
        // TODO フォローカウントの更新

        // ユーザーに対してのフォロー数を取得
        val tsk: Task<QuerySnapshot> = _followRepository.select_byuserId(userEntity.UserId)
        _followRepository.execing(tsk)
        if(_followRepository.isSuccessed(tsk)) {
            // 成功
            val cnt: Int = _followRepository.getResultEntiryCount(tsk)

            FollowView?.text = cnt.toString()
        }
    }

    fun updateFollowerView(userEntity: UserEntity){
        // TODO フォローカウントの更新

        // ユーザーに対してのフォロワー数を取得
        val tsk: Task<QuerySnapshot> = _followRepository.select_byfollowerId(userEntity.UserId)
        _followRepository.execing(tsk)
        if(_followRepository.isSuccessed(tsk)) {
            // 成功
            val cnt: Int = _followRepository.getResultEntiryCount(tsk)

            FollowerView?.text = cnt.toString()
        }
    }


}