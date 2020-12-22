package com.example.bookintroapp.helper

import androidx.fragment.app.Fragment
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.SigninForm
import com.example.bookintroapp.valueobject.form.SignupForm
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

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

        fun selectUserEntity(frag: Fragment, _userRepository: IUserRepository): UserEntity?{
            try {
                // TODO アクティビティに保存してるメールアドレスからユーザデータ取得
                val ac: MainActivity = frag.activity as MainActivity
                val emailString = ac.getSigninMail()

                // ユーザーエンティティ
                var tsk: Task<QuerySnapshot> = _userRepository.select_byEmail(emailString)
                _userRepository.execing(tsk)
                return _userRepository.getResultEntityQ(tsk)
            }catch(ex: ClassCastException){
                return null
            }
        }

        fun selectUserEntity(id: String, _userRepository: IUserRepository): UserEntity?{
            try {
                // TODO アクティビティに保存してるメールアドレスからユーザデータ取得

                // ユーザーエンティティ
                var tsk: Task<DocumentSnapshot> = _userRepository.select_byId(id)
                _userRepository.execing(tsk)
                return _userRepository.getResultEntityD(tsk)
            }catch(ex: ClassCastException){
                return null
            }
        }

        fun selectBookEntity(id: String, _bookRepository: IBookRepository) : BookEntity?{
            try {
                // TODO アクティビティに保存してる書籍IDから書籍データ取得

                // ユーザーエンティティ
                var tsk: Task<DocumentSnapshot> = _bookRepository.select_byId(id)
                _bookRepository.execing(tsk)
                return _bookRepository.getResultEntity(tsk)
            }catch(ex: ClassCastException){
                return null
            }
        }

    }
}