package com.example.bookintroapp.valueobject.button

import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.adapter.ViewHolder
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.NiceEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// いいねボタン
class NiceCntButton() {

    // リポジトリ
    private val _niceRepository: INiceRepository = NiceRepository()

    fun getNiceCntCount(bookEntity: BookEntity): String{
        // TODO 各ブックマークの合計を取得
        val tsk: Task<QuerySnapshot> = _niceRepository.select_byBookId(bookEntity.BookId)
        _niceRepository.execing(tsk)
        if(tsk.isSuccessful){
            val count: Int = _niceRepository.getResultEntiryCount(tsk)
            return count.toString()
        }
        else {
            return ""
        }
    }

    fun isNiceCnt_byUser(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO 自身のユーザがブックマーク登録したかチェック
        val tsk: Task<QuerySnapshot> = _niceRepository.select_byuserId_bookId(userEntity.UserId, bookEntity.BookId)
        _niceRepository.execing(tsk)
        if(tsk.isSuccessful){
            val count: Int = _niceRepository.getResultEntiryCount(tsk)
            return count == 0
        }else{
            return false
        }
    }

    fun OnNiceCntEventlistener(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO いいね押下時のイベント処理

        // いいねリスト登録
        val entityNew = NiceEntity("0", userEntity.UserId, bookEntity.BookId, Date())
        val tskAdd: Task<DocumentReference> = _niceRepository.insert(entityNew)
        _niceRepository.execing(tskAdd)
        return tskAdd.isSuccessful


        /*


        bookEntity.plus_niceCnt()

        // firebase更新
        val tsk: Task<Void> =  _bookRepository.update_niceCnt_byId(bookEntity.BookId!!, bookEntity.NiceCnt)
        _bookRepository.execing(tsk)
        return tsk.isSuccessful

         */
    }

}