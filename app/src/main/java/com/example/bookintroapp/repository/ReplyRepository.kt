package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Timestamp
import java.util.*

// リプライリポジトリ
class ReplyRepository : IReplyRepository {

    companion object{
        val REPLY_TABLE : String             = "replyTable"
        val REPLY_TABLE_ID: String           = "replyId"
        val REPLY_TABLE_BOOKID: String       = "bookId"
        val REPLY_TABLE_USERID: String       = "userId"
        val REPLY_TABLE_SATIS: String        = "satis"
        val REPLY_TABLE_NICECNT: String      = "niceCnt"
        val REPLY_TABLE_COMMENT: String      = "comment"
        val REPLY_TABLE_CREATED: String      = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(REPLY_TABLE)
        return collection.get()
    }

    override fun select_byId(id: String): Task<DocumentSnapshot> {
        // TODO IDによる選択
        val document = FirebaseHelpler.getDocument(REPLY_TABLE, id)
        return document.get()
    }

    override fun select_bybookId(bookId: String): Task<QuerySnapshot> {
        // TODO 書籍IDによる選択
        val collection = FirebaseHelpler.getCollection(REPLY_TABLE)
        return collection
                .whereEqualTo(REPLY_TABLE_BOOKID, bookId)
                .get()
    }

    override fun select_byuserId_bookId(userId: String, bookId: String): Task<QuerySnapshot> {
        // TODO ユーザID,書籍IDによる選択
        val collection = FirebaseHelpler.getCollection(REPLY_TABLE)
        return collection
                .whereEqualTo(REPLY_TABLE_USERID, userId)
                .whereEqualTo(REPLY_TABLE_BOOKID, bookId)
                .get()
    }

    override fun insert(entity: ReplyEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                REPLY_TABLE_BOOKID to entity.BookId,
                REPLY_TABLE_USERID to entity.UserId,
                REPLY_TABLE_NICECNT to entity.NiceCnt,
                REPLY_TABLE_COMMENT to entity.Comment,
                REPLY_TABLE_SATIS to entity.Satis,
                REPLY_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(REPLY_TABLE)
        var tsk: Task<DocumentReference> = collection.add(data)
        return tsk
    }

    override fun update_niceCnt_byId(id: String, cnt: Int): Task<Void> {
        // TODO いいねカウンタの更新(更新対象:ID)
        val document = FirebaseHelpler.getDocument(REPLY_TABLE, id)
        return document.update(REPLY_TABLE_NICECNT, cnt)
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

    override fun getResultEntity(tsk: Task<DocumentSnapshot>): ReplyEntity? {
        // TODO エンティティの生成
        var entity: ReplyEntity? = null
        if(tsk.isSuccessful){
            val doc: DocumentSnapshot? = tsk.result
            entity = createEntity(doc!!)
        }
        return entity
    }

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<ReplyEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<ReplyEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: ReplyEntity? = createEntity(doc)
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

    override fun changeBindingList(list: MutableList<ReplyEntity>): MutableList<Map<String, String>> {
        // TODO エンティティリストの生成
        var mapList: MutableList<Map<String, String>> = mutableListOf()
        for(entity in list){
            var map = mapOf(
                    REPLY_TABLE_BOOKID to entity.BookId,
                    REPLY_TABLE_USERID to entity.UserId,
                    REPLY_TABLE_NICECNT to entity.NiceCntDisplay,
                    REPLY_TABLE_SATIS to entity.SatisDisplay,
                    REPLY_TABLE_COMMENT to entity.Comment,
                    REPLY_TABLE_CREATED to entity.Created.toString()
            )
            mapList.add(map)
        }
        list.clear()
        return mapList
    }

    private fun createEntity(doc: QueryDocumentSnapshot): ReplyEntity? {
        // TODO エンティティの生成
        var entity: ReplyEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(REPLY_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = ReplyEntity(
                doc.id,
                doc.data?.get(REPLY_TABLE_USERID).toString(),
                doc.data?.get(REPLY_TABLE_BOOKID).toString(),
                doc.data?.get(REPLY_TABLE_COMMENT).toString(),
                doc.data?.get(REPLY_TABLE_NICECNT).toString().toInt(),
                doc.data?.get(REPLY_TABLE_SATIS).toString().toInt(),
                date
        )
        return entity
    }

    private fun createEntity(doc: DocumentSnapshot): ReplyEntity? {
        // TODO エンティティの生成
        var entity: ReplyEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(REPLY_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = ReplyEntity(
                doc.id,
                doc.data?.get(REPLY_TABLE_USERID).toString(),
                doc.data?.get(REPLY_TABLE_BOOKID).toString(),
                doc.data?.get(REPLY_TABLE_COMMENT).toString(),
                doc.data?.get(REPLY_TABLE_NICECNT).toString().toInt(),
                doc.data?.get(REPLY_TABLE_SATIS).toString().toInt(),
                date
        )
        return entity
    }
}