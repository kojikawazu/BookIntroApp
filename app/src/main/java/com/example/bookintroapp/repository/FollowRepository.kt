package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.FollowEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Timestamp
import java.util.*

// フォローリポジトリ
class FollowRepository : IFollowRepository {

    companion object{
        val FOLLOW_TABLE : String             = "followTable"
        val FOLLOW_TABLE_ID: String           = "followId"
        val FOLLOW_TABLE_USERID: String       = "userId"
        val FOLLOW_TABLE_FOLLOWERID: String   = "followerId"
        val FOLLOW_TABLE_CREATED: String      = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(FOLLOW_TABLE)
        return collection.get()
    }

    override fun select_byId(id: String): Task<DocumentSnapshot> {
        // TODO IDによる選択
        val document = FirebaseHelpler.getDocument(FOLLOW_TABLE, id)
        return document.get()
    }

    override fun select_byuserId(userId: String): Task<QuerySnapshot> {
        // TODO 書籍IDによる選択
        val collection = FirebaseHelpler.getCollection(FOLLOW_TABLE)
        return collection
                .whereEqualTo(FOLLOW_TABLE_USERID, userId)
                .get()
    }

    override fun select_byfollowerId(followerId: String): Task<QuerySnapshot> {
        // TODO フォロワーIDによる選択
        val collection = FirebaseHelpler.getCollection(FOLLOW_TABLE)
        return collection
            .whereEqualTo(FOLLOW_TABLE_FOLLOWERID, followerId)
            .get()
    }

    override fun insert(entity: FollowEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                FOLLOW_TABLE_USERID to entity.UserId,
                FOLLOW_TABLE_FOLLOWERID to entity.FollowerId,
                FOLLOW_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(FOLLOW_TABLE)
        var tsk: Task<DocumentReference> = collection.add(data)
        return tsk
    }

    override fun execing(tsk: Task<*>) {
        // TODO セレクト終わるまでループ
        while(!tsk.isComplete){ }
    }

    override fun isSuccessed(tsk: Task<*>): Boolean {
        // TODO 成功したかどうかをチェック
        return (tsk.isSuccessful)
    }

    override fun getResultEntiryCount(tsk: Task<QuerySnapshot>): Int {
        // TODO エンティティの数を取得
        if(tsk.isSuccessful){
            val result: QuerySnapshot? = tsk.result
            val count = result?.size()
            return count!!
        }else{
            return 0
        }
    }

    override fun getResultEntity(tsk: Task<DocumentSnapshot>): FollowEntity? {
        // TODO エンティティの生成
        var entity: FollowEntity? = null
        if(tsk.isSuccessful){
            val doc: DocumentSnapshot? = tsk.result
            entity = createEntity(doc!!)
        }
        return entity
    }

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<FollowEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<FollowEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: FollowEntity? = createEntity(doc)
                    if(entity != null){
                        list.add(entity)
                    }
                }
            }
        }
        else{
            // 失敗
            goError(tsk)
        }
        return list
    }

    override fun goError(tsk: Task<QuerySnapshot>) {
        // TODO 失敗処理
        var exception = tsk.exception
        Log.d(Constraints.TAG,"DB error is $exception")
    }

    override fun changeBindingList(list: MutableList<FollowEntity>): MutableList<Map<String, String>> {
        // TODO エンティティリストの生成
        var mapList: MutableList<Map<String, String>> = mutableListOf()
        for(entity in list){
            var map = mapOf(
                    FOLLOW_TABLE_USERID to entity.UserId,
                    FOLLOW_TABLE_FOLLOWERID to entity.FollowerId,
                    FOLLOW_TABLE_CREATED to entity.Created.toString()
            )
            mapList.add(map)
        }
        list.clear()
        return mapList
    }

    private fun createEntity(doc: QueryDocumentSnapshot): FollowEntity? {
        // TODO エンティティの生成
        var entity: FollowEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(FOLLOW_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = FollowEntity(
                doc.id,
                doc.data?.get(FOLLOW_TABLE_USERID).toString(),
                doc.data?.get(FOLLOW_TABLE_FOLLOWERID).toString(),
                date
        )
        return entity
    }

    private fun createEntity(doc: DocumentSnapshot): FollowEntity? {
        // TODO エンティティの生成
        var entity: FollowEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(FOLLOW_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = FollowEntity(
                doc.id,
                doc.data?.get(FOLLOW_TABLE_USERID).toString(),
                doc.data?.get(FOLLOW_TABLE_FOLLOWERID).toString(),
                date
        )
        return entity
    }
}