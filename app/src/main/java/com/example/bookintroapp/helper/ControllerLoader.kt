package com.example.bookintroapp.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.activity.BookIntroActivity
import com.example.bookintroapp.activity.ChangePasswdActivity
import com.example.bookintroapp.activity.SignupActivity

class ControllerLoader() {

    init{

    }

    // TODO static
    companion object{
        val ACTIVITY_SIGNUP       = "SignupActivity"
        val ACTIVITY_CHANGEPASSWD = "ChangePasswdActivity"
        val ACTIVITY_BOOK_MAIN    = "BookIntroActivity"

        fun GetActivity(activity : AppCompatActivity, nextActivity : String){
            // TODO クラス名によるアクティビティクラスオブジェクトを返す
            var intent: Intent? = null;
            when(nextActivity){
                ACTIVITY_SIGNUP -> {
                    intent = Intent(activity, SignupActivity::class.java)
                }
                ACTIVITY_CHANGEPASSWD ->{
                    intent = Intent(activity, ChangePasswdActivity::class.java)
                }
                ACTIVITY_BOOK_MAIN ->{
                    intent = Intent(activity, BookIntroActivity::class.java)
                }
                else ->{
                    intent = null
                }
            }

            if(intent != null)
                activity.startActivity(intent)
        }
    }



}