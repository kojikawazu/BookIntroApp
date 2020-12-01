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

    override fun setListener(view: View, flag: Fragment) {
        // TODO イベントリスナー追加

        // クリックリスナーの設定
        var buttonChangePS = view.findViewById<Button>(R.id.signin_chpasswd_button)
        buttonChangePS.setOnClickListener{
            // TODO パスワード変更タップ
            ActivityHelper.nextFragment(flag, R.id.action_signin_to_chpasswd_fragment)
        }

        view.findViewById<Button>(R.id.signin_signup_button).apply{
            setOnClickListener {
                // TODO サインアップボタンリスナー
                ActivityHelper.nextFragment(flag, R.id.action_signin_to_signup_fragment)
            }
        }

        view.findViewById<Button>(R.id.signin_button).apply {
            setOnClickListener {
                // TODO サインインタップ
                onClickListener_signin(view, flag)
            }
        }
    }

    fun onClickListener_signin(view: View, flag: Fragment) {
        // TODO サインインボタンリスナー

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        var errorString = isValidate(flag)

        // エラーチェック
        if( !errorString.isEmpty() ){
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(flag, errorString)
            return ;
        }

        // Firebaseのサインイン処理
        // ----------------------------------------------------------------------------------------
        var tsk: Task<AuthResult> = FirebaseHelpler.authSignin(signinForm!!.EmailString, signinForm!!.PasswdString)
        while(!(tsk.isComplete)){}
        if(!tsk.isSuccessful){
            // サインイン失敗
            ActivityHelper.show_error_dialog(flag, ActivityHelper.getStringDefine(flag, R.string.signin_error_dialog))
            return
        }

        // サインイン成功
        // ----------------------------------------------------------------------------------------
        ActivityHelper.nextFragment(flag, R.id.action_signin_to_bookmain_fragment)
    }

    fun isValidate(flag: Fragment) : String{
        // TODO バリデーションチェック

        // 空チェック
        if( signinForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_empty)
        }

        // メールアドレスチェック
        if( !signinForm!!.checkEmail() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_email)
        }

        // 成功時は空白返す
        return ""
    }

}