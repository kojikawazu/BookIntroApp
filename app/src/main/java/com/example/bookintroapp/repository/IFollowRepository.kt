package com.example.bookintroapp.repository

import com.example.bookintroapp.valueobject.entity.FollowEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface IFollowRepository {

    fun selectAll(): Task<QuerySnapshot>

    fun select_byId(id: String): Task<DocumentSnapshot>

    fun select_byuserId(userId: String): Task<QuerySnapshot>

    fun select_byfollowerId(followerId: String): Task<QuerySnapshot>

    fun select_byuserId_followerId(userId: String, followerId: String): Task<QuerySnapshot>

    fun insert(entity: FollowEntity) : Task<DocumentReference>

    fun execing(tsk: Task<*>)

    fun isSuccessed(tsk: Task<*>): Boolean

    fun getResultEntiryCount(tsk: Task<QuerySnapshot>): Int

    fun getResultEntity(tsk: Task<DocumentSnapshot>): FollowEntity?

    fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<FollowEntity>

    fun goError(tsk: Task<QuerySnapshot>)

    fun changeBindingList(list: MutableList<FollowEntity>): MutableList<Map<String, String>>

}