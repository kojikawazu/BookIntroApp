package com.example.bookintroapp.valueobject.button

import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.adapter.ViewHolder
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.google.android.gms.tasks.Task

// いいねボタン
class NiceCntButton() {

    // リポジトリ
    private val _bookRepository: IBookRepository = BookRepository()

    fun OnNiceCntEventlistener(bookEntity: BookEntity): Boolean{
        // TODO いいね押下時のイベント処理
        bookEntity.plus_niceCnt()

        // firebase更新
        val tsk: Task<Void> =  _bookRepository.update_niceCnt_byId(bookEntity.BookId!!, bookEntity.NiceCnt)
        _bookRepository.execing(tsk)
        return tsk.isSuccessful
    }

}