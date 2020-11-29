package com.example.bookintroapp.model

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.SignupForm
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// サインインモデル
class SignupModel : ModelBase() {

    // フォーム
    private var signupForm : SignupForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO レイアウト設定

        // テキストのバインド
        signupForm = SignupForm(
                view.findViewById(R.id.signup_user_edit),
                view.findViewById(R.id.signup_mail_edit),
                view.findViewById(R.id.signup_passwd_edit),
                view.findViewById(R.id.signup_passwd2_edit),
                view.findViewById(R.id.signup_forgot_edit)
        )
    }

    override fun setLayout(activity: AppCompatActivity) {

    }

    override fun setListener(view: View, flag: Fragment) {
        // TODO イベントリスナー追加

        // サインアップボタン
        view.findViewById<Button>(R.id.signup_button).apply {
            setOnClickListener{
                // TODO パスワード変更タップ
                onClickListener_signup(view, flag)
            }
        }
    }

    override fun setListener(activity: AppCompatActivity) {

    }

    fun onClickListener_signup(view: View, flag: Fragment){
        // TODO サインアップタップ

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        var errorString = isValidate(flag)

        // エラーチェック
        if( !errorString.isEmpty() ){
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(flag, errorString)
            return ;
        }

        // 既に登録されてるかチェック
        // ----------------------------------------------------------------------------------------
        // セレクトタスク設定
        var entityCheck: UserEntity? = null
        var tskSelect: Task<QuerySnapshot> = _userRepository.select_byEmail(signupForm!!.EmailString)
        // 終わるまでループ
        while(!tskSelect.isComplete){ }
        // 終了したら処理
        entityCheck = _userRepository.getResultEntity(tskSelect)
        if( entityCheck != null ){
            // 既にユーザ登録済み
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(flag, ActivityHelper.getStringDefine(flag,R.string.signup_error_dialog_user))
            return ;
        }

        // Firebaseへユーザ登録処理
        // ----------------------------------------------------------------------------------------
        var tskAuthSignup: Task<AuthResult> = FirebaseHelpler.authSignup(signupForm!!.EmailString, signupForm!!.PasswdNewString)
        while(!tskAuthSignup.isComplete){}
        if( !tskAuthSignup.isSuccessful ){
            // 追加に失敗
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(flag, ActivityHelper.getStringDefine(flag,R.string.signup_error_dialog) + tskAuthSignup.exception)
            return ;
        }

        // ユーザ登録DB処理
        // ----------------------------------------------------------------------------------------
        var dateNow: Date = Date()
        var entityNew: UserEntity = UserEntity("0",
                signupForm!!.UserNameString, signupForm!!.EmailString,
                signupForm!!.PasswdForgotString, dateNow)

        var tskAdd: Task<DocumentReference> = _userRepository.insert(entityNew)
        while(!tskAdd.isComplete){}
        if( !tskAdd.isSuccessful ){
            // 追加に失敗
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(flag, ActivityHelper.getStringDefine(flag, R.string.signup_error_dialog) + "2")
            return ;
        }

        // 追加登録完了
        // ----------------------------------------------------------------------------------------
        // サインアップ成功ダイアログ
        ActivityHelper.show_success_dialog(flag,
                R.string.signup_success_title, R.string.signup_success_contents) {
            // OKの場合、サインアップ画面閉じる
            ActivityHelper.backFragment(flag)
        }
    }

    fun isValidate(flag: Fragment) : String{
        // TODO バリデーションチェック
        // 空チェック
        if( signupForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_empty)
        }

        // 名前チェック
        val name_max : Int = ActivityHelper.getIntDefine(flag, R.string.signup_username_len)
        if( !signupForm!!.checkUserName(name_max) ){
            return "ユーザ名の文字数が" + name_max.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_name_max)
        }

        // メールアドレスチェック
        if( !signupForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_email)
        }

        // パスワードチェック
        val passwd_len : Int = ActivityHelper.getIntDefine(flag, R.string.signup_passwd_len)
        if( !signupForm!!.checkNewPasswd_len(passwd_len)){
            return "パスワードの文字数が" + passwd_len.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_passwd_len)
        }
        if( !signupForm!!.checkNewPasswd_same()){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_passwd_same)
        }

        // 忘れた時用パスワードチェック
        val forgot_len : Int = ActivityHelper.getIntDefine(flag, R.string.signup_passwd_len)
        if( !signupForm!!.checkForgotPasswd_len(forgot_len)){
            return "忘れた時用のパスワードの文字数が" + forgot_len.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_forgot_len)
        }

        // 成功時は空白返す
        return ""
    }

    fun onClickListener_signup(activity: AppCompatActivity){
        // TODO サインアップタップ


    }

    fun isValidate(activity: AppCompatActivity) : String{
        // TODO バリデーションチェック

        // 成功時は空白返す
        return ""
    }

}