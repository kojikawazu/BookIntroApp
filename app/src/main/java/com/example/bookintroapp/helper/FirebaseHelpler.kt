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
        val USER_TABLE : String           = "userTable"
        val USER_TABLE_NAME: String       = "user"
        val USER_TABLE_EMAIL: String      = "email"
        val USER_TABLE_FORGOTMAIL: String = "forgotPasswd"
        val USER_TABLE_CREATED: String    = "created"

        fun getCollection(): CollectionReference {
            val db = FirebaseFirestore.getInstance()
            return db.collection(USER_TABLE)
        }

        fun getAuth(){
            FirebaseAuth.getInstance()
        }

        fun authSignin(email: String, passwd: String) : Task<AuthResult>{
            // TODO サインイン処理
            var auth: FirebaseAuth = FirebaseAuth.getInstance()
            return auth.signInWithEmailAndPassword(email, passwd)
        }

        fun authSignup(email: String, passwd: String) : Task<AuthResult>{
            // TODO サインアップ処理
            var auth: FirebaseAuth = FirebaseAuth.getInstance()
            return auth.createUserWithEmailAndPassword(email, passwd)
        }

    }
}