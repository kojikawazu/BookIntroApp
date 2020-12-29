package com.example.bookintroapp.valueobject.button

import android.widget.Button
import android.widget.TextView
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.entity.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// 返信用いいねボタン
class NiceCntReplyButton {

    // リポジトリ
    private val _replyRepository: IReplyRepository = ReplyRepository()
    private val _niceReplyRepository: INiceReplyRepository = NiceReplyRepository()

    fun OnNiceCntClickListener(niceCntView: TextView, niceCntButton: Button, userEntity: UserEntity, replyEntity: ReplyEntity){
        // TODO いいねボタン押下処理

        // いいねデータ追加
        val ret = InsertNiceCnt(userEntity, replyEntity)
        if(ret){
            // 追加成功

            // いいね数を更新
            replyEntity.setNiceCnt(getNiceCntCount(replyEntity).toInt())
            // ビューに反映
            niceCntView.text = replyEntity.NiceCntDisplay

            // 書籍テーブルのいいねカウンタの更新
            val tsk: Task<Void> = _replyRepository.update_niceCnt_byId(replyEntity.ReplyId, replyEntity.NiceCnt)
            _replyRepository.execing(tsk)

            // UI更新
            niceCntButton.isEnabled = isNiceCnt_byUser(userEntity, replyEntity)
        }
    }

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

    fun InsertNiceCnt(userEntity: UserEntity, replyEntity: ReplyEntity): Boolean{
        // TODO いいねデータの追加

        // いいねリスト登録
        val entityNew = NiceReplyEntity(replyEntity, userEntity, Date())
        val tskAdd: Task<DocumentReference> = _niceReplyRepository.insert(entityNew)
        _niceReplyRepository.execing(tskAdd)
        return _niceReplyRepository.isSuccessed(tskAdd)
    }
}