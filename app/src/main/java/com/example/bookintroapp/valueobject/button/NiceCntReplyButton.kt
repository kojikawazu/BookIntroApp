package com.example.bookintroapp.valueobject.button

import com.example.bookintroapp.repository.INiceReplyRepository
import com.example.bookintroapp.repository.INiceRepository
import com.example.bookintroapp.repository.NiceReplyRepository
import com.example.bookintroapp.repository.NiceRepository
import com.example.bookintroapp.valueobject.entity.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// 返信用いいねボタン
class NiceCntReplyButton {

    // リポジトリ
    private val _niceReplyRepository: INiceReplyRepository = NiceReplyRepository()

    fun getNiceCntCount(replyEntity: ReplyEntity): String{
        // TODO 各ブックマークの合計を取得
        val tsk: Task<QuerySnapshot> = _niceReplyRepository.select_byReplyId(replyEntity.ReplyId)
        _niceReplyRepository.execing(tsk)
        if(_niceReplyRepository.isSuccessed(tsk)){
            val count: Int = _niceReplyRepository.getResultEntiryCount(tsk)
            return count.toString()
        }
        else {
            return ""
        }
    }

    fun isNiceCnt_byUser(userEntity: UserEntity, replyEntity: ReplyEntity): Boolean{
        // TODO 自身のユーザがブックマーク登録したかチェック
        val tsk: Task<QuerySnapshot> = _niceReplyRepository.select_byuserId_replyId(userEntity.UserId, replyEntity.ReplyId)
        _niceReplyRepository.execing(tsk)
        if(_niceReplyRepository.isSuccessed(tsk)){
            val count: Int = _niceReplyRepository.getResultEntiryCount(tsk)
            return count == 0
        }else{
            return false
        }
    }

    fun OnNiceCntEventlistener(userEntity: UserEntity, replyEntity: ReplyEntity): Boolean{
        // TODO いいね押下時のイベント処理

        // いいねリスト登録
        val entityNew = NiceReplyEntity(replyEntity, userEntity, Date())
        val tskAdd: Task<DocumentReference> = _niceReplyRepository.insert(entityNew)
        _niceReplyRepository.execing(tskAdd)
        return _niceReplyRepository.isSuccessed(tskAdd)
    }
}