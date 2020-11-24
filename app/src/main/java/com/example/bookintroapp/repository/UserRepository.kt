package com.example.bookintroapp.repository

import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.helper.FirebaseHelpler
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.Date
import java.sql.Timestamp

class UserRepository : IUserRepository {

    override fun selectAll():  MutableList<UserEntity> {
        // TODO 全てユーザ選択
        var list: MutableList<UserEntity> = mutableListOf()
        val collection = FirebaseHelpler.getCollection()

        collection.get()
                    .addOnSuccessListener { result ->
                        for(doc in result) {
                            var entity: UserEntity? = this.createEntity(doc)
                            if(entity != null){
                                list.add(entity)
                            }
                        }
                    }
                    .addOnFailureListener{ exception ->
                        Log.d(TAG,"DB error is $exception")
                    }
        return list
    }

    override fun select_byEmail(email: String): Task<QuerySnapshot> {
        // TODO 名前とメールアドレスの一致確認
        val collection = FirebaseHelpler.getCollection()
        var tsk: Task<QuerySnapshot> =  collection
                .whereEqualTo(FirebaseHelpler.USER_TABLE_EMAIL, email)
                .get()
        return tsk
    }

    override fun select_byEmailForgotPasswd(email: String, forgotPasswd: String): Task<QuerySnapshot> {
        // TODO 名前とメールアドレスと忘れた用のパスワードの一致確認
        val collection = FirebaseHelpler.getCollection()
        var tsk: Task<QuerySnapshot> =  collection
            .whereEqualTo(FirebaseHelpler.USER_TABLE_EMAIL, email)
            .whereEqualTo(FirebaseHelpler.USER_TABLE_FORGOTMAIL, forgotPasswd)
            .get()
        return tsk
    }

    override fun insert(entity: UserEntity): Task<DocumentReference> {
        // TODO データ追加処理
        val data = hashMapOf(
            FirebaseHelpler.USER_TABLE_NAME to entity.UserName,
            FirebaseHelpler.USER_TABLE_EMAIL to entity.Email,
            FirebaseHelpler.USER_TABLE_FORGOTMAIL to entity.ForgotPasswd,
            FirebaseHelpler.USER_TABLE_CREATED to Timestamp(entity.Created.time)
        )
        val collection = FirebaseHelpler.getCollection()
        var tsk: Task<DocumentReference> = collection.add(data)
        return tsk
    }

    override fun getResultEntity(tsk: Task<QuerySnapshot>): UserEntity?{
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
            var stamp: com.google.firebase.Timestamp = doc.data?.get(FirebaseHelpler.USER_TABLE_CREATED) as com.google.firebase.Timestamp
            var date: Date = stamp.toDate()
            entity = UserEntity(
                doc.id,
                doc.data?.get(FirebaseHelpler.USER_TABLE_NAME).toString(),
                doc.data?.get(FirebaseHelpler.USER_TABLE_EMAIL).toString(),
                doc.data?.get(FirebaseHelpler.USER_TABLE_FORGOTMAIL).toString(),
                date
            )
        }
        return entity
    }

}