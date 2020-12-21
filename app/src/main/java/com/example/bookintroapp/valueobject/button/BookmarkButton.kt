package com.example.bookintroapp.valueobject.button

import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IMarkRepository
import com.example.bookintroapp.repository.MarkRepository
import com.example.bookintroapp.valueobject.adapter.ViewHolder
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class BookmarkButton() {

    // リポジトリ
    private val _markRepository: IMarkRepository = MarkRepository()

    fun getBookMarkCount(bookEntity: BookEntity): String{
        // TODO 各ブックマークの合計を取得
        val tsk: Task<QuerySnapshot> = _markRepository.select_byBookId(bookEntity.BookId)
        _markRepository.execing(tsk)
        if(_markRepository.isSuccessed(tsk)){
            val count: Int = _markRepository.getResultEntiryCount(tsk)
            return count.toString()
        }
        else {
            return ""
        }
    }

    fun isBookMark_byUser(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO 自身のユーザがブックマーク登録したかチェック
        val tsk: Task<QuerySnapshot> = _markRepository.select_byuserId_bookId(userEntity.UserId, bookEntity.BookId)
        _markRepository.execing(tsk)
        if(_markRepository.isSuccessed(tsk)){
            val count: Int = _markRepository.getResultEntiryCount(tsk)
            return count == 0
        }else{
            return false
        }
    }

    fun OnBookMarkEventListener(userEntity: UserEntity, bookEntity: BookEntity): Boolean{
        // TODO ブックマークボタンのイベント処理

        // ブックマーク登録
        val entityNew = MarkEntity("0", userEntity.UserId, bookEntity.BookId, Date())
        val tskAdd: Task<DocumentReference> = _markRepository.insert(entityNew)
        _markRepository.execing(tskAdd)
        return _markRepository.isSuccessed(tskAdd)
    }
}