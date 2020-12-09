package com.example.bookintroapp.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelpler {

    init{

    }

    // TODO static
    companion object{

        fun getCollection(table: String): CollectionReference {
            val db = FirebaseFirestore.getInstance()
            return db.collection(table)
        }

        fun getAuth(): FirebaseAuth{
            // TODO 認証インスタンスの取得
            return FirebaseAuth.getInstance()
        }

        fun authSignin(email: String, passwd: String) : Task<AuthResult>{
            // TODO サインイン処理
            var auth: FirebaseAuth = getAuth()
            return auth.signInWithEmailAndPassword(email, passwd)
        }

        fun authSignup(email: String, passwd: String) : Task<AuthResult>{
            // TODO サインアップ処理
            var auth: FirebaseAuth = getAuth()
            return auth.createUserWithEmailAndPassword(email, passwd)
        }

        fun authChangePasswd(email: String) : Task<Void>{
            // TODO パスワード変更メールを送信
            var auth: FirebaseAuth = getAuth()
            return auth.sendPasswordResetEmail(email)
        }

        fun authSignout(){
            // TODO サインアウト処理
            var auth: FirebaseAuth = getAuth()
            auth.signOut()
        }

    }
}