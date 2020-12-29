package com.example.bookintroapp.valueobject.form.common

import android.widget.TextView
import com.example.bookintroapp.valueobject.entity.UserEntity

// タイトルフォーム
class TitleForm() {

    constructor(titleText: TextView) : this() {
        // TODO コンストラクタ
        TitleText = titleText

    }

    // getter
    private var TitleText : TextView? = null
        get(){
            return field
        }
    val TitleTextString : String
        get(){
            if(TitleText != null){   return TitleText!!.text.toString()  }
            else{                       return ""                       }
        }

    fun setTitle_bookmain(userName: String){
        // TODO メインページ時のタイトル設定
        TitleText?.text = userName + "さんのメインページ"
    }

    fun setTitle_bookmark(userName: String){
        // TODO ブックマーク時のタイトル設定
        TitleText?.text = userName + "さんのブックマークリスト"
    }

    fun setTitle_mypage(userName: String){
        // TODO マイページ時のタイトル設定
        TitleText?.text = userName + "さんの書籍紹介"
    }

    fun setTitle_follower(followerName: String){
        // TODO 別ユーザのタイトル設定
        TitleText?.text = followerName + "さんの書籍紹介"
    }


}