package com.example.bookintroapp.model

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.valueobject.form.SigninForm
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.ControllerLoader
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SigninModel : ModelBase() {

    // フォーム
    private var signinForm: SigninForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()

    init{
        // TODO 初期化

    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
        signinForm = SigninForm(
                view.findViewById(R.id.signin_mail_edit),
                view.findViewById(R.id.signin_passwd_edit)
        )
    }

    override fun setLayout(activity: AppCompatActivity) {
        TODO("Not yet implemented")
    }

    override fun setListener(view: View, flag: Fragment) {
        // TODO イベントリスナー追加

        // クリックリスナーの設定
        var buttonChangePS = view.findViewById<Button>(R.id.signin_chpasswd_button)
        buttonChangePS.setOnClickListener{
            // TODO パスワード変更タップ
            //onClickListener_changePasswd(view)
        }
        var buttonSignup = view.findViewById<Button>(R.id.signin_signup_button)
        buttonSignup.setOnClickListener {
            // TODO サインアップボタンリスナー
            flag.findNavController().navigate(R.id.action_signin_to_signup_fragment)
        }

        var buttonSignin =view.findViewById<Button>(R.id.signin_button)
        buttonSignin.setOnClickListener {
            // TODO サインインタップ
            //onClickListener_signin(view)
        }
    }

    override fun setListener(activity: AppCompatActivity) {
        TODO("Not yet implemented")
    }

    fun onClickListener_changePasswd(activity: AppCompatActivity){
        // TODO パスワード変更ボタンリスナー
        ControllerLoader.GetActivity(activity, ControllerLoader.ACTIVITY_CHANGEPASSWD)
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