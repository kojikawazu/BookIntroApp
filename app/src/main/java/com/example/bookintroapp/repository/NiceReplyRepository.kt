package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.NiceEntity
import com.example.bookintroapp.valueobject.entity.NiceReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Timestamp
import java.util.*

// リプライいいねリポジトリ
class NiceReplyRepository : INiceReplyRepository {

    companion object{
        val NICE_REPLY_TABLE : String                    = "niceReplyTable"
        val NICE_REPLY_TABLE_REPLYID: String             = "replyId"
        val NICE_REPLY_TABLE_USERID: String              = "userId"
        val NICE_REPLY_TABLE_CREATED: String             = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(NICE_REPLY_TABLE)
        return collection.get()
    }

    override fun select_byId(id: String): Task<QuerySnapshot> {
        TODO("Not yet implemented")
    }

    override fun select_byReplyId(replyId: String): Task<QuerySnapshot> {
        // TODO リプライIDによる選択
        val collection = FirebaseHelpler.getCollection(NICE_REPLY_TABLE)
        return collection
                .whereEqualTo(NICE_REPLY_TABLE_REPLYID, replyId)
                .get()
    }

    override fun insert(entity: NiceReplyEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                NICE_REPLY_TABLE_REPLYID to entity.ReplyId,
                NICE_REPLY_TABLE_USERID to entity.UserId,
                NICE_REPLY_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(NICE_REPLY_TABLE)
        val tsk: Task<DocumentReference> = collection.add(data)
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

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<NiceReplyEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<NiceReplyEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            val result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: NiceReplyEntity? = createEntity(doc)
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

    override fun goError(tsk: Task<QuerySnapshot>) {
        // TODO 失敗処理
        var exception = tsk.exception
        Log.d(Constraints.TAG,"DB error is $exception")
    }

    private fun createEntity(doc: QueryDocumentSnapshot): NiceReplyEntity? {
        // TODO エンティティの生成
        var entity: NiceReplyEntity? = null
        if(doc != null){
            // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
            // 対処   : キャストの仕方変更
            var stamp: com.google.firebase.Timestamp = doc.data?.get(NICE_REPLY_TABLE_CREATED) as com.google.firebase.Timestamp
            var date: Date = stamp.toDate()
            entity = NiceReplyEntity(
                    doc.id,
                    doc.data?.get(NICE_REPLY_TABLE_REPLYID).toString(),
                    doc.data?.get(NICE_REPLY_TABLE_USERID).toString(),
                    date
            )
        }
        return entity
    }
}