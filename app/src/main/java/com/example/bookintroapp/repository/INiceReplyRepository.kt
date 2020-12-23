package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.NiceEntity
import com.example.bookintroapp.valueobject.entity.NiceReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface INiceReplyRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byId(id: String): Task<QuerySnapshot>

    fun select_byReplyId(replyId: String): Task<QuerySnapshot>

    fun select_byuserId_replyId(userId: String, replyId: String): Task<QuerySnapshot>

    fun insert(entity: NiceReplyEntity) : Task<DocumentReference>

    fun execing(tsk: Task<*>)

    fun isSuccessed(tsk: Task<*>): Boolean

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<NiceReplyEntity>

    fun getResultEntiryCount(tsk: Task<QuerySnapshot>): Int

    fun goError(tsk: Task<QuerySnapshot>)

}