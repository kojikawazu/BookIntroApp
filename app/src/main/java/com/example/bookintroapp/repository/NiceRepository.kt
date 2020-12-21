package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.NiceEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Timestamp
import java.util.*

// いいねリポジトリ
class NiceRepository : INiceRepository {

    companion object{
        val NICE_TABLE : String                   = "niceTable"
        val NICE_TABLE_USERID: String             = "userId"
        val NICE_TABLE_BOOKID: String             = "bookId"
        val NICE_TABLE_CREATED: String            = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(NICE_TABLE)
        return collection.get()
    }

    override fun select_byuserId(userId: String): Task<QuerySnapshot> {
        // TODO ユーザIDによる選択
        val collection = FirebaseHelpler.getCollection(NICE_TABLE)
        return collection
                .whereEqualTo(NICE_TABLE_USERID, userId)
                .get()
    }

    override fun select_byBookId(bookId: String): Task<QuerySnapshot> {
        // TODO 書籍IDによる選択
        val collection = FirebaseHelpler.getCollection(NICE_TABLE)
        return collection
                .whereEqualTo(NICE_TABLE_BOOKID, bookId)
                .get()
    }

    override fun select_byuserId_bookId(userId: String, bookId: String): Task<QuerySnapshot> {
        // TODO ユーザID,書籍IDによる選択
        val collection = FirebaseHelpler.getCollection(NICE_TABLE)
        return collection
                .whereEqualTo(NICE_TABLE_USERID, userId)
                .whereEqualTo(NICE_TABLE_BOOKID, bookId)
                .get()
    }

    override fun insert(entity: NiceEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                NICE_TABLE_USERID to entity.UserId,
                NICE_TABLE_BOOKID to entity.BookId,
                NICE_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(NICE_TABLE)
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

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<NiceEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<NiceEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            val result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: NiceEntity? = createEntity(doc)
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

    private fun createEntity(doc: QueryDocumentSnapshot): NiceEntity? {
        // TODO エンティティの生成
        var entity: NiceEntity? = null
        if(doc != null){
            // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
            // 対処   : キャストの仕方変更
            var stamp: com.google.firebase.Timestamp = doc.data?.get(NICE_TABLE_CREATED) as com.google.firebase.Timestamp
            var date: Date = stamp.toDate()
            entity = NiceEntity(
                    doc.id,
                    doc.data?.get(NICE_TABLE_USERID).toString(),
                    doc.data?.get(NICE_TABLE_BOOKID).toString(),
                    date
            )
        }
        return entity
    }
}