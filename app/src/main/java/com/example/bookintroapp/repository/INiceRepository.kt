package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.NiceEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface INiceRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byuserId(userId: String): Task<QuerySnapshot>

    fun select_byBookId(bookId: String): Task<QuerySnapshot>

    fun select_byuserId_bookId(userId: String, bookId: String): Task<QuerySnapshot>

    fun insert(entity: NiceEntity) : Task<DocumentReference>

    fun execing(tsk: Task<*>)

    fun isSuccessed(tsk: Task<*>): Boolean

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<NiceEntity>

    fun getResultEntiryCount(tsk: Task<QuerySnapshot>): Int

    fun goError(tsk: Task<QuerySnapshot>)

}