package com.example.bookintroapp.valueobject.form.form

import android.view.View
import android.widget.*
import com.example.bookintroapp.helper.DomainHelper
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity

// リプライフォーム
class BookReplyForm() {

    constructor(contentsText: TextView, bookText: TextView, titleText: TextView,
                commentText: EditText, satisSpinner: Spinner, replyButton: Button) : this(){
        // TODO コンストラクタ
        ContentsText = contentsText
        BookText = bookText
        TitleText = titleText
        CommentText = commentText
        SatisSpinner = satisSpinner
        ReplyButton = replyButton

        // スピナー設定
        SatisSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // 選択された状態
                SatisString = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 選択してない状態
            }
        }
    }

    // getter
    private var ContentsText : TextView? = null
        get(){
            return field
        }
    private var BookText : TextView? = null
        get(){
            return field
        }
    private var TitleText : TextView? = null
        get(){
            return field
        }
    private var CommentText : EditText? = null
        get(){
            return field
        }
    private var SatisSpinner : Spinner? = null
        get(){
            return field
        }
    private var ReplyButton : Button? = null
        get(){
            return field
        }
    val CommentString : String
        get(){
            if(CommentText != null){   return CommentText!!.text.toString()  }
            else{                       return ""                       }
        }
    var SatisString : String = "1"
        get(){          return field    }
        set(value){     field = value   }

    fun setData(userEntity: UserEntity, bookEntity: BookEntity){
        // TODO データの設定
        ContentsText?.text = userEntity.UserName + "さんに返信"
        BookText?.text = bookEntity.BookName
        TitleText?.text = bookEntity.BookTitle
    }

    fun isEmpty(): Boolean {
        // TODO 空チェック
        return ( CommentText?.text.toString().isEmpty() )
    }

    fun checkComment(max : Int) : Boolean{
        // TODO コメントチェック
        if(CommentText != null){
            var len : Int = CommentText!!.text.toString().length
            return ( DomainHelper.IsRange(len, max) )
        }
        return false
    }

    fun setOnClickListener(func: () -> Unit){
        // TODO クリックリスナーの設定
        ReplyButton?.apply {
            // TODO リプライ実行タップ
            setOnClickListener {
                func()
            }
        }
    }

}