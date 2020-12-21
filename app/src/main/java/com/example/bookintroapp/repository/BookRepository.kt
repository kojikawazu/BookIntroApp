package com.example.bookintroapp.repository

import android.util.Log
import android.widget.SimpleAdapter
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

// 書籍リポジトリ
class BookRepository : IBookRepository {

    companion object{
        val BOOK_TABLE : String             = "bookTable"
        val BOOK_TABLE_USERID: String       = "userId"
        val BOOK_TABLE_TITLE: String        = "title"
        val BOOK_TABLE_NAME: String         = "bookName"
        val BOOK_TABLE_SATIS: String        = "satis"
        val BOOK_TABLE_NICECNT: String      = "niceCnt"
        val BOOK_TABLE_MARKCNT: String      = "markCnt"
        val BOOK_TABLE_COMMENT: String      = "comment"
        val BOOK_TABLE_CREATED: String      = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(BOOK_TABLE)
        return collection.get()
    }

    override fun select_byId(id: String): Task<DocumentSnapshot> {
        // TODO IDによる選択
        val document = FirebaseHelpler.getDocument(BOOK_TABLE, id)
        return document.get()
    }

    override fun select_byuserId(userId: String): Task<QuerySnapshot> {
        // TODO ユーザIDによる選択
        val collection = FirebaseHelpler.getCollection(BOOK_TABLE)
        return collection
                .whereEqualTo(BOOK_TABLE_USERID, userId)
                .get()
    }

    override fun insert(entity: BookEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                BOOK_TABLE_NAME to entity.BookName,
                BOOK_TABLE_TITLE to entity.BookTitle,
                BOOK_TABLE_SATIS to entity.SatisCnt,
                BOOK_TABLE_NICECNT to entity.NiceCnt,
                BOOK_TABLE_MARKCNT to entity.MarkCnt,
                BOOK_TABLE_COMMENT to entity.Comment,
                BOOK_TABLE_USERID to entity.UserId,
                BOOK_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(BOOK_TABLE)
        var tsk: Task<DocumentReference> = collection.add(data)
        return tsk
    }

    override fun update_niceCnt_byId(id: String, cnt: Int) : Task<Void> {
        // TODO いいねカウンタの更新(更新対象:ID)
        val document = FirebaseHelpler.getDocument(BOOK_TABLE, id)
        return document.update(BOOK_TABLE_NICECNT, cnt)
    }

    override fun update_markCnt_byId(id: String, cnt: Int): Task<Void> {
        // TODO ブックマークカウンタの更新(更新対象:ID)
        val document = FirebaseHelpler.getDocument(BOOK_TABLE, id)
        return document.update(BOOK_TABLE_MARKCNT, cnt)
    }

    override fun execing(tsk: Task<*>) {
        // TODO セレクト終わるまでループ
        while(!tsk.isComplete){ }
    }

    override fun isSuccessed(tsk: Task<*>): Boolean {
        // TODO 成功したかどうかをチェック
       return (tsk.isSuccessful)
    }

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<BookEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<BookEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: BookEntity? = createEntity(doc)
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

    override fun getResultEntity(tsk: Task<DocumentSnapshot>): BookEntity?{
        // TODO エンティティの生成
        var entity: BookEntity? = null
        if(tsk.isSuccessful){
            val doc: DocumentSnapshot? = tsk.result
            entity = createEntity(doc!!)
        }
        return entity
    }

    override fun goError(tsk: Task<QuerySnapshot>) {
        // TODO 失敗処理
        var exception = tsk.exception
        Log.d(Constraints.TAG,"DB error is $exception")
    }

    override fun changeBindingList(list: MutableList<BookEntity>): MutableList<Map<String, String>> {
        // TODO エンティティリストの生成
        var mapList: MutableList<Map<String, String>> = mutableListOf()
        for(entity in list){
            var map = mapOf(
                    BOOK_TABLE_TITLE to entity.BookTitle,
                    BOOK_TABLE_SATIS to entity.SatisCntDisplay,
                    BOOK_TABLE_NICECNT to entity.NiceCntDisplay,
                    BOOK_TABLE_MARKCNT to entity.MarkCntDisplay,
                    BOOK_TABLE_COMMENT to entity.Comment,
                    BOOK_TABLE_CREATED to entity.Created.toString()
            )
            mapList.add(map)
        }
        list.clear()
        return mapList
    }

    private fun createEntity(doc: QueryDocumentSnapshot): BookEntity? {
        // TODO エンティティの生成
        var entity: BookEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(BOOK_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = BookEntity(
                doc.id,
                doc.data?.get(BOOK_TABLE_USERID).toString(),
                doc.data?.get(BOOK_TABLE_NAME).toString(),
                doc.data?.get(BOOK_TABLE_TITLE).toString(),
                doc.data?.get(BOOK_TABLE_SATIS).toString().toInt(),
                doc.data?.get(BOOK_TABLE_NICECNT).toString().toInt(),
                doc.data?.get(BOOK_TABLE_MARKCNT).toString().toInt(),
                doc.data?.get(BOOK_TABLE_COMMENT).toString(),
                date
        )
        return entity
    }

    private fun createEntity(doc: DocumentSnapshot): BookEntity? {
        // TODO エンティティの生成
        var entity: BookEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(BOOK_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = BookEntity(
                doc.id,
                doc.data?.get(BOOK_TABLE_USERID).toString(),
                doc.data?.get(BOOK_TABLE_NAME).toString(),
                doc.data?.get(BOOK_TABLE_TITLE).toString(),
                doc.data?.get(BOOK_TABLE_SATIS).toString().toInt(),
                doc.data?.get(BOOK_TABLE_NICECNT).toString().toInt(),
                doc.data?.get(BOOK_TABLE_MARKCNT).toString().toInt(),
                doc.data?.get(BOOK_TABLE_COMMENT).toString(),
                date
        )
        return entity
    }
}