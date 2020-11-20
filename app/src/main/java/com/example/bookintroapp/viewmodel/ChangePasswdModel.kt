package com.example.bookintroapp.viewmodel

import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R
import com.example.bookintroapp.form.ChangePasswdForm
import com.example.bookintroapp.helper.ActivityHelper

class ChangePasswdModel : ViewModelBase() {

    private var chpasswdForm : ChangePasswdForm? = null

    init{
        // TODO 初期化
    }



    override fun setLayout(activity: AppCompatActivity) {
        // TODO レイアウト設定

        // テキストのバインド
        chpasswdForm = ChangePasswdForm(
                activity.findViewById(R.id.passwd_mail_edit),
                activity.findViewById(R.id.passwd_forgot_edit),
                activity.findViewById(R.id.passwd_new_edit),
                activity.findViewById(R.id.passwd_check_edit)
        )

        // 戻るボタン追加
        ActivityHelper.setLayout_gobackButton(activity)
    }

    override fun setListener(activity: AppCompatActivity) {
        // TODO イベントリスナー追加
        // パスワード変更ボタン
        var buttonSignup = activity.findViewById<Button>(R.id.passwd_button)
        buttonSignup.setOnClickListener{
            // TODO パスワード変更タップ
            onClickListener_changepasswd(activity)
        }
    }

    fun onClickListener_changepasswd(activity: AppCompatActivity){
        // TODO パスワード変更タップ

        // バリデーションチェック
        var errorString = isValidate(activity)

        // エラーチェック
        if( !errorString.isEmpty() ){
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(activity, errorString)
            return ;
        }

        // パスワード変更成功ダイアログ
        ActivityHelper.show_success_dialog(activity,
                R.string.passwd_success_title, R.string.passwd_success_contents) {
            // OKの場合、パスワード変更画面閉じる
            activity.finish()
        }
    }

    fun isValidate(activity: AppCompatActivity) : String{
        // TODO バリデーションチェック

        // 空チェック
        if( chpasswdForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_empty)
        }

        // メールアドレスチェック
        if( !chpasswdForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_email)
        }

        // 忘れた時用パスワードチェック
        val forgot_len : Int = ActivityHelper.getIntDefine(activity, R.string.signup_passwd_len)
        if( !chpasswdForm!!.checkForgotPasswd_len(forgot_len)){
            return "忘れた時用のパスワードの文字数が" + forgot_len.toString() + ActivityHelper.getStringDefine(activity, R.string.error_form_forgot_len)
        }

        // パスワードチェック
        val passwd_len : Int = ActivityHelper.getIntDefine(activity, R.string.signup_passwd_len)
        if( !chpasswdForm!!.checkNewPasswd_len(passwd_len)){
            return "パスワードの文字数が" + passwd_len.toString() + ActivityHelper.getStringDefine(activity, R.string.error_form_passwd_len)
        }
        if( !chpasswdForm!!.checkNewPasswd_same()){
            return ActivityHelper.getStringDefine(activity, R.string.error_form_passwd_same)
        }

        // 成功時は空白返す
        return ""
    }

}