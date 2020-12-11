package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface IMarkRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byuserId(userId: String): Task<QuerySnapshot>

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<MarkEntity>

    fun goError(tsk: Task<QuerySnapshot>)

}