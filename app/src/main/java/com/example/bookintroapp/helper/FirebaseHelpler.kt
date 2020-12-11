package com.example.bookintroapp.helper

import com.example.bookintroapp.valueobject.form.SigninForm
import com.example.bookintroapp.valueobject.form.SignupForm
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelpler {

    init{

    }

    // TODO static
    companion object{

        fun getCollection(table: String): CollectionReference {
            // TODO テーブル取得
            val db = FirebaseFirestore.getInstance()
            return db.collection(table)
        }

        fun getDocument(table: String, doc: String) : DocumentReference{
            // TODO ドキュメント取得
            val db = FirebaseFirestore.getInstance()
            return db.collection(table).document(doc)
        }

        fun getAuth(): FirebaseAuth{
            // TODO 認証インスタンスの取得
            return FirebaseAuth.getInstance()
        }

        fun authSignin(signinForm: SigninForm) : Task<AuthResult>{
            // TODO サインイン処理
            var auth: FirebaseAuth = getAuth()
            return auth.signInWithEmailAndPassword(signinForm.EmailString, signinForm.PasswdString)
        }

        fun authSignup(signupForm: SignupForm) : Task<AuthResult>{
            // TODO サインアップ処理
            var auth: FirebaseAuth = getAuth()
            return auth.createUserWithEmailAndPassword(signupForm!!.EmailString, signupForm!!.PasswdForgotString)
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