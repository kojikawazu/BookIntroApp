package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.Date
import java.sql.Timestamp

// ユーザリポジトリ
class UserRepository : IUserRepository {

    companion object{
        val USER_TABLE : String           = "userTable"
        val USER_TABLE_NAME: String       = "user"
        val USER_TABLE_EMAIL: String      = "email"
        val USER_TABLE_FORGOTMAIL: String = "forgotPasswd"
        val USER_TABLE_CREATED: String    = "created"
    }

    override fun selectAll(): Task<QuerySnapshot> {
        // TODO 全てユーザ選択
        val collection = FirebaseHelpler.getCollection(USER_TABLE)
        return collection.get()
    }

    override fun select_byId(id: String): Task<DocumentSnapshot> {
        // TODO IDによる選択
        val document = FirebaseHelpler.getDocument(USER_TABLE, id)
        return document.get()
    }

    override fun select_byEmail(email: String): Task<QuerySnapshot> {
        // TODO 名前とメールアドレスの一致確認
        val collection = FirebaseHelpler.getCollection(USER_TABLE)
        return collection
                .whereEqualTo(UserRepository.USER_TABLE_EMAIL, email)
                .get()
    }

    override fun select_byEmailForgotPasswd(email: String, forgotPasswd: String): Task<QuerySnapshot> {
        // TODO 名前とメールアドレスと忘れた用のパスワードの一致確認
        val collection = FirebaseHelpler.getCollection(USER_TABLE)
        var tsk: Task<QuerySnapshot> =  collection
            .whereEqualTo(UserRepository.USER_TABLE_EMAIL, email)
            .whereEqualTo(UserRepository.USER_TABLE_FORGOTMAIL, forgotPasswd)
            .get()
        return tsk
    }

    override fun insert(entity: UserEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
                USER_TABLE_NAME to entity.UserName,
                USER_TABLE_EMAIL to entity.Email,
                USER_TABLE_FORGOTMAIL to entity.ForgotPasswd,
                USER_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection(USER_TABLE)
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

    override fun getResultEntityD(tsk: Task<DocumentSnapshot>): UserEntity? {
        // TODO エンティティの生成
        var entity: UserEntity? = null
        if(tsk.isSuccessful){
            val doc: DocumentSnapshot? = tsk.result
            entity = createEntity(doc!!)
        }
        return entity
    }

    override fun getResultEntityQ(tsk: Task<QuerySnapshot>): UserEntity?{
        // TODO 選択の結果を取得
        var entity: UserEntity? = null
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                var len = result.size()
                if( len == 1 ) {
                    for (doc in result) {
                        entity = createEntity(doc)
                        break
                    }
                }
            }
        }
        else{
            // 失敗
            goError(tsk)
        }
        return entity
    }

    override fun getResultEntityList(tsk: Task<QuerySnapshot>): MutableList<UserEntity> {
        // TODO 選択の結果を取得
        val list: MutableList<UserEntity> = mutableListOf()
        if(tsk.isSuccessful){
            // 成功
            var result: QuerySnapshot? = tsk.result
            if(result != null){
                var len = result.size()
                if( len == 1 ) {
                    for (doc in result) {
                        var entity: UserEntity? = createEntity(doc)
                        if(entity != null){
                            list.add(entity)
                        }
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
        Log.d(TAG,"DB error is $exception")
    }

    private fun createEntity(doc: QueryDocumentSnapshot): UserEntity? {
        // TODO エンティティの生成
        var entity: UserEntity? = null
        if(doc != null){
            // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
            // 対処   : キャストの仕方変更
            var stamp: com.google.firebase.Timestamp = doc.data?.get(UserRepository.USER_TABLE_CREATED) as com.google.firebase.Timestamp
            var date: Date = stamp.toDate()
            entity = UserEntity(
                doc.id,
                doc.data?.get(UserRepository.USER_TABLE_NAME).toString(),
                doc.data?.get(UserRepository.USER_TABLE_EMAIL).toString(),
                doc.data?.get(UserRepository.USER_TABLE_FORGOTMAIL).toString(),
                date
            )
        }
        return entity
    }

    private fun createEntity(doc: DocumentSnapshot): UserEntity? {
        // TODO エンティティの生成
        var entity: UserEntity?

        // エラー : com.google.firebase.Timestampをjava.sql.Timestampにキャストできません
        // 対処   : キャストの仕方変更
        var stamp: com.google.firebase.Timestamp = doc.data?.get(USER_TABLE_CREATED) as com.google.firebase.Timestamp
        var date: Date = stamp.toDate()
        entity = UserEntity(
                doc.id,
                doc.data?.get(USER_TABLE_NAME).toString(),
                doc.data?.get(USER_TABLE_EMAIL).toString(),
                doc.data?.get(USER_TABLE_FORGOTMAIL).toString(),
                date
        )
        return entity
    }

}