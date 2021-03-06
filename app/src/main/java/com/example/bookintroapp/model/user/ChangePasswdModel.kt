package com.example.bookintroapp.model.user

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.valueobject.form.form.ChangePasswdForm
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.model.base.ModelBase
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

// パスワード変更モデル
class ChangePasswdModel : ModelBase() {

    // フォーム
    private var chpasswdForm : ChangePasswdForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO レイアウト設定

        // テキストのバインド
        chpasswdForm = ChangePasswdForm(
                view.findViewById(R.id.passwd_mail_edit),
                view.findViewById(R.id.passwd_forgot_edit),
                view.findViewById(R.id.passwd_new_edit),
                view.findViewById(R.id.passwd_check_edit),
                view.findViewById(R.id.passwd_button)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // パスワード変更ボタン
        chpasswdForm?.setOnClickListener {
            // TODO パスワード変更タップ
            onClickListener_changepasswd(view, frag)
        }
    }

    fun onClickListener_changepasswd(view: View, frag: Fragment){
        // TODO パスワード変更タップ

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        val isError : Boolean = ActivityHelper.checkValidate(frag) { isValidate(frag) }
        // エラーチェック
        if( !isError ){
            return
        }

        //　メールアドレスチェック + 忘れた時用パスワードチェック
        // ----------------------------------------------------------------------------------------
        val tskSelect: Task<QuerySnapshot> = _userRepository.select_byEmailForgotPasswd(chpasswdForm!!.EmailString, chpasswdForm!!.ForgotString)
        // セレクト中
        _userRepository.execing(tskSelect)
        // 終了したら処理
        val entityCheck = _userRepository.getResultEntityQ(tskSelect)
        if( entityCheck == null ){
            // メールアドレスと忘れた時用パスワードない
            ActivityHelper.show_error_dialog(frag, ActivityHelper.getStringDefine(frag,R.string.passwd_error_dialog_nosame))
            return
        }

        //　パスワード変更
        // ----------------------------------------------------------------------------------------
        val tskChpasswd: Task<Void> = FirebaseHelpler.authChangePasswd(chpasswdForm!!.EmailString)
        while(!(tskChpasswd.isComplete)){}
        if(!tskChpasswd.isSuccessful){
            // メール送信失敗
            ActivityHelper.show_error_dialog(frag, ActivityHelper.getStringDefine(frag, R.string.passwd_error_dialog_nosend))
            return
        }

        // パスワード変更成功ダイアログ
        ActivityHelper.show_success_dialog(frag,
                R.string.passwd_success_title, R.string.passwd_success_contents) {
            // OKの場合、パスワード変更画面閉じる
            ActivityHelper.backFragment(frag)
        }
    }

    fun isValidate(flag: Fragment) : String{
        // TODO バリデーションチェック

        // 空チェック
        if( chpasswdForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_empty)
        }

        // メールアドレスチェック
        if( !chpasswdForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_email)
        }

        // 忘れた時用パスワードチェック
        val forgot_len : Int = ActivityHelper.getIntDefine(flag, R.string.signup_passwd_len)
        if( !chpasswdForm!!.checkForgotPasswd_len(forgot_len)){
            return "忘れた時用のパスワードの文字数が" + forgot_len.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_forgot_len)
        }

        // パスワードチェック
        val passwd_len : Int = ActivityHelper.getIntDefine(flag, R.string.signup_passwd_len)
        if( !chpasswdForm!!.checkNewPasswd_len(passwd_len)){
            return "パスワードの文字数が" + passwd_len.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_passwd_len)
        }
        if( !chpasswdForm!!.checkNewPasswd_same()){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_passwd_same)
        }

        // 成功時は空白返す
        return ""
    }
}