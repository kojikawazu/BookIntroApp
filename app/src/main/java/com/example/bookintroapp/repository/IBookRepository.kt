package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface IBookRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byuserId(userId: String): Task<QuerySnapshot>

    //fun insert(entity: BookEntity) : Task<DocumentReference>

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<BookEntity>

    fun goError(tsk: Task<QuerySnapshot>)

    fun changeBindingList(list: MutableList<BookEntity>): MutableList<Map<String, String>>

}