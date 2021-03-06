package com.example.bookintroapp.model.user

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.UserActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.model.base.ModelBase
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.valueobject.form.form.SigninForm
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

// サインインモデル
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

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        view.findViewById<Button>(R.id.signin_chpasswd_button).apply {
            setOnClickListener{
                // TODO パスワード変更タップ
                ActivityHelper.nextFragment(frag, R.id.action_signin_to_chpasswd_fragment)
            }
        }

        view.findViewById<Button>(R.id.signin_signup_button).apply{
            setOnClickListener {
                // TODO サインアップボタンリスナー
                ActivityHelper.nextFragment(frag, R.id.action_signin_to_signup_fragment)
            }
        }

        view.findViewById<Button>(R.id.signin_button).apply {
            setOnClickListener {
                // TODO サインインタップ
                onClickListener_signin(view, frag)
            }
        }
    }

    fun onClickListener_signin(view: View, frag: Fragment) {
        // TODO サインインボタンリスナー

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        val isError : Boolean = ActivityHelper.checkValidate(frag) { isValidate(frag) }
        // エラーチェック
        if( !isError ){
            return
        }

        // Firebaseのサインイン処理
        // ----------------------------------------------------------------------------------------
        var tsk: Task<AuthResult> = FirebaseHelpler.authSignin(signinForm!!)
        while(!(tsk.isComplete)){}
        if(!tsk.isSuccessful){
            // サインイン失敗
            ActivityHelper.show_error_dialog(frag, R.string.signin_error_dialog)
            return
        }

        // サインイン成功
        // ----------------------------------------------------------------------------------------
        val userActivity: UserActivity = frag.activity as UserActivity
        userActivity.changeMainActivity()
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