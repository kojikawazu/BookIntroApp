package com.example.bookintroapp.model

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.ChangePasswdForm
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class ChangePasswdModel : ModelBase() {

    private var chpasswdForm : ChangePasswdForm? = null
    private val _userRepository: IUserRepository = UserRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        TODO("Not yet implemented")
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

    override fun setListener(view: View, flag: Fragment) {
        TODO("Not yet implemented")
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

        //　メールアドレスチェック + 忘れた時用パスワードチェック
        // ----------------------------------------------------------------------------------------
        var entityCheck: UserEntity? = null
        var tskSelect: Task<QuerySnapshot> = _userRepository.select_byEmailForgotPasswd(chpasswdForm!!.EmailString, chpasswdForm!!.ForgotString)
        // 終わるまでループ
        while(!tskSelect.isComplete){ }
        // 終了したら処理
        entityCheck = _userRepository.getResultEntity(tskSelect)
        if( entityCheck == null ){
            // メールアドレスと忘れた時用パスワードない
            ActivityHelper.show_error_dialog(activity, ActivityHelper.getStringDefine(activity,R.string.passwd_error_dialog_nosame))
            return ;
        }

        //　パスワード変更
        // ----------------------------------------------------------------------------------------
        var tskChpasswd: Task<Void> = FirebaseHelpler.authChangePasswd(chpasswdForm!!.EmailString)
        while(!(tskChpasswd.isComplete)){}
        if(!tskChpasswd.isSuccessful){
            // メール送信失敗
            ActivityHelper.show_error_dialog(activity, ActivityHelper.getStringDefine(activity, R.string.passwd_error_dialog_nosend))
            return
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