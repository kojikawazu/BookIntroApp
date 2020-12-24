package com.example.bookintroapp.valueobject.button

import android.widget.Button
import android.widget.TextView
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
    private val _bookRepository: IBookRepository = BookRepository()

    fun OnNiceCntEventlistener(niceCntView: TextView, niceCntButton: Button, userEntity : UserEntity, bookEntity: BookEntity){
        // TODO いいねボタン押下処理

        // いいねデータ追加
        val ret = InsertNiceCnt(userEntity, bookEntity)
        if(ret){
            // 追加成功

            // いいね数を更新
            bookEntity.setNiceCnt(getNiceCntCount(bookEntity).toInt())
            // ビューに反映
            niceCntView.text = bookEntity.NiceCntDisplay

            // 書籍テーブルのいいねカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_niceCnt_byId(bookEntity.BookId, bookEntity.NiceCnt)
            _bookRepository.execing(tsk)

            // UI更新
            updateNiceCntButton(niceCntButton, userEntity, bookEntity)
        }
    }

    fun getNiceCntCount(bookEntity: BookEntity): String{
        // TODO いいねの合計を取得
        val tsk: Task<QuerySnapshot> = _niceRepository.select_byBookId(bookEntity.BookId)
        _niceRepository.execing(tsk)
        if(_niceRepository.isSuccessed(tsk)){
            val count: Int = _niceRepository.getResultEntiryCount(tsk)
            return count.toString()
        }
        else {
            return ""
        }
    }

    fun isNiceCnt_byUser(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO 自身のユーザがいいね登録したかチェック
        val tsk: Task<QuerySnapshot> = _niceRepository.select_byuserId_bookId(userEntity.UserId, bookEntity.BookId)
        _niceRepository.execing(tsk)
        if(_niceRepository.isSuccessed(tsk)){
            val count: Int = _niceRepository.getResultEntiryCount(tsk)
            return count == 0
        }else{
            return false
        }
    }

    fun InsertNiceCnt(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO いいね押下時のイベント処理

        // いいねリスト登録
        val entityNew = NiceEntity(userEntity, bookEntity, Date())
        val tskAdd: Task<DocumentReference> = _niceRepository.insert(entityNew)
        _niceRepository.execing(tskAdd)
        return _niceRepository.isSuccessed(tskAdd)
    }

    fun updateNiceCntButton(niceCntButton: Button, userEntity : UserEntity, bookEntity: BookEntity){
        // TODO ボタンUI更新
        niceCntButton.isEnabled = isNiceCnt_byUser(userEntity, bookEntity)
    }

}