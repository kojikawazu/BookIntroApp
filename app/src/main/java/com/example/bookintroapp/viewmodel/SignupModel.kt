package com.example.bookintroapp.viewmodel

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R
import com.example.bookintroapp.form.SignupForm
import com.example.bookintroapp.helper.ActivityHelper

class SignupModel : ViewModelBase() {

    private var signupForm : SignupForm? = null

    init{
        // TODO 初期化

    }

    override fun setLayout(activity: AppCompatActivity) {
        // TODO レイアウト設定

        // テキストのバインド
        signupForm = SignupForm(
                activity.findViewById(R.id.signup_user_edit),
                activity.findViewById(R.id.signup_mail_edit),
                activity.findViewById(R.id.signup_passwd_edit),
                activity.findViewById(R.id.signup_passwd2_edit),
                activity.findViewById(R.id.signup_forgot_edit)
        )

        // 戻るボタン追加
        ActivityHelper.setLayout_gobackButton(activity)


    }

    override fun setListener(activity: AppCompatActivity) {
        // TODO イベントリスナー追加
        // サインアップボタン
        var buttonSignup = activity.findViewById<Button>(R.id.signup_button)
        buttonSignup.setOnClickListener{
            // TODO パスワード変更タップ
            onClickListener_signup(activity)
        }
    }

    fun onClickListener_signup(activity: AppCompatActivity){
        // TODO サインアップタップ

        // バリデーションチェック
        var errorString = isValidate(activity)

        // エラーチェック
        if( !errorString.isEmpty() ){
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(activity, errorString)
            return ;
        }

        // サインアップ成功ダイアログ
        ActivityHelper.show_success_dialog(activity,
                R.string.signup_success_title, R.string.signup_success_contents) {
            // OKの場合、サインアップ画面閉じる
            activity.finish()
        }

    }

    fun isValidate(activity: AppCompatActivity) : String{
        // TODO バリデーションチェック

        // 空チェック
        if( signupForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_empty)
        }

        // 名前チェック
        val name_max : Int = ActivityHelper.getIntDefine(activity, R.string.signup_username_len)
        if( !signupForm!!.checkUserName(name_max) ){
            return "ユーザ名の文字数が" + name_max.toString() + ActivityHelper.getStringDefine(activity, R.string.error_form_name_max)
        }

        // メールアドレスチェック
        if( !signupForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_email)
        }

        // パスワードチェック
        val passwd_len : Int = ActivityHelper.getIntDefine(activity, R.string.signup_passwd_len)
        if( !signupForm!!.checkNewPasswd_len(passwd_len)){
            return "パスワードの文字数が" + passwd_len.toString() + ActivityHelper.getStringDefine(activity, R.string.error_form_passwd_len)
        }
        if( !signupForm!!.checkNewPasswd_same()){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_passwd_same)
        }

        // 忘れた時用パスワードチェック
        val forgot_len : Int = ActivityHelper.getIntDefine(activity, R.string.signup_passwd_len)
        if( !signupForm!!.checkForgotPasswd_len(forgot_len)){
            return "忘れた時用のパスワードの文字数が" + forgot_len.toString() + ActivityHelper.getStringDefine(activity, R.string.error_form_forgot_len)
        }

        // 成功時は空白返す
        return ""
    }

}