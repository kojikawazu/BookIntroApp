package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface IUserRepository {

    fun selectAll(): MutableList<UserEntity>

    fun select_byEmail(email: String): Task<QuerySnapshot>

    fun select_byEmailForgotPasswd(email: String, forgotPasswd: String): Task<QuerySnapshot>

    fun insert(entity: UserEntity) : Task<DocumentReference>

    fun getResultEntity(tsk: Task<QuerySnapshot>): UserEntity?

    fun goError(tsk: Task<QuerySnapshot>)

}