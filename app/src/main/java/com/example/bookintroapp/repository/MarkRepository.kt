package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// ブックマークリポジトリ
class MarkRepository : IMarkRepository {

    companion object{
        val MARK_TABLE : String                   = "bookmarkTable"
        val MARK_TABLE_USERID: String             = "userId"
        val MARK_TABLE_BOOKID: String             = "bookId"
        val MARK_TABLE_CREATED: String            = "created"
    }


    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(MARK_TABLE)
        return collection.get()
    }

    override fun select_byuserId(userId: String): Task<QuerySnapshot> {
        // TODO ユーザIDによる選択
        val collection = FirebaseHelpler.getCollection(MARK_TABLE)
        return collection
                .whereEqualTo(MARK_TABLE_USERID, userId)
                .get()
    }

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<MarkEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<MarkEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                for (doc in result) {
                    var entity: MarkEntity? = createEntity(doc)
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

    private fun createEntity(doc: QueryDocumentSnapshot): MarkEntity? {
        // TODO エンティティの生成
        var entity: MarkEntity? = null
        if(doc != null){
            // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
            // 対処   : キャストの仕方変更
            var stamp: com.google.firebase.Timestamp = doc.data?.get(MARK_TABLE_CREATED) as com.google.firebase.Timestamp
            var date: Date = stamp.toDate()
            entity = MarkEntity(
                    doc.id,
                    doc.data?.get(MARK_TABLE_USERID).toString(),
                    doc.data?.get(MARK_TABLE_BOOKID).toString(),
                    date
            )
        }
        return entity
    }
}