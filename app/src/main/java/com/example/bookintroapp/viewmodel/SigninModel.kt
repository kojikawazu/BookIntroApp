package com.example.bookintroapp.viewmodel

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R
import com.example.bookintroapp.form.SigninForm
import com.example.bookintroapp.form.SignupForm
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.ControllerLoader
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult

class SigninModel : ViewModelBase() {

    // フォーム
    private var signinForm: SigninForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()

    init{
        // TODO 初期化

    }

    override fun setLayout(activity: AppCompatActivity) {
        // TODO ビューの設定
        signinForm = SigninForm(
                activity.findViewById(R.id.signin_mail_edit),
                activity.findViewById(R.id.signin_passwd_edit)
        )
    }

    override fun setListener(activity: AppCompatActivity) {
        // TODO イベントリスナー追加

        // クリックリスナーの設定
        var buttonChangePS = activity.findViewById<Button>(R.id.signin_chpasswd_button)
        buttonChangePS.setOnClickListener{
            // TODO パスワード変更タップ
            onClickListener_changePasswd(activity)
        }
        var buttonSignup =activity.findViewById<Button>(R.id.signin_signup_button)
        buttonSignup.setOnClickListener {
            // TODO サインアップタップ
            onClickListener_signup(activity)
        }

        var buttonSignin =activity.findViewById<Button>(R.id.signin_button)
        buttonSignin.setOnClickListener {
            // TODO サインアップタップ
            onClickListener_signin(activity)
        }
    }

    fun onClickListener_changePasswd(activity: AppCompatActivity){
        // TODO パスワード変更ボタンリスナー
        ControllerLoader.GetActivity(activity, ControllerLoader.ACTIVITY_CHANGEPASSWD)
    }

    fun onClickListener_signup(activity: AppCompatActivity){
        // TODO サインアップボタンリスナー
        ControllerLoader.GetActivity(activity, ControllerLoader.ACTIVITY_SIGNUP)
    }

    fun onClickListener_signin(activity: AppCompatActivity){
        // TODO サインインボタンリスナー

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        var errorString = isValidate(activity)

        // エラーチェック
        if( !errorString.isEmpty() ){
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(activity, errorString)
            return ;
        }

        // Firebaseのサインイン処理
        // ----------------------------------------------------------------------------------------
        var tsk: Task<AuthResult> = FirebaseHelpler.authSignin(signinForm!!.EmailString, signinForm!!.PasswdString)
        while(!(tsk.isComplete)){}
        if(!tsk.isSuccessful){
            // サインイン失敗
            ActivityHelper.show_error_dialog(activity, ActivityHelper.getStringDefine(activity, R.string.signin_error_dialog))
            return
        }

        // サインイン成功
        // ----------------------------------------------------------------------------------------
        ControllerLoader.GetActivity(activity, ControllerLoader.ACTIVITY_BOOK_MAIN)
    }

    fun isValidate(activity: AppCompatActivity) : String{
        // TODO バリデーションチェック

        // 空チェック
        if( signinForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_empty)
        }

        // メールアドレスチェック
        if( !signinForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_email)
        }

        // 成功時は空白返す
        return ""
    }

}