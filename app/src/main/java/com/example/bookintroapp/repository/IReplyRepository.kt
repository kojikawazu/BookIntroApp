package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface IReplyRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byId(id: String): Task<DocumentSnapshot>

    fun select_bybookId(bookId: String): Task<QuerySnapshot>

    fun select_byuserId_bookId(userId: String, bookId: String): Task<QuerySnapshot>

    fun insert(entity: ReplyEntity) : Task<DocumentReference>

    fun execing(tsk: Task<*>)

    fun isSuccessed(tsk: Task<*>): Boolean

    fun getResultEntiryCount(tsk: Task<QuerySnapshot>): Int

    fun getResultEntity(tsk: Task<DocumentSnapshot>): ReplyEntity?

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<ReplyEntity>

    fun goError(tsk: Task<QuerySnapshot>)

    fun changeBindingList(list: MutableList<ReplyEntity>): MutableList<Map<String, String>>

}