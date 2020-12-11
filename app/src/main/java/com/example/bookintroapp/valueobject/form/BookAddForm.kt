package com.example.bookintroapp.valueobject.form

import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import com.example.bookintroapp.helper.DomainHelper

// 書籍追加フォーム
class BookAddForm {

    init{
        // TODO 初期化
    }

    constructor(bookNameEdit: EditText, titleEdit: EditText,
                commentEdit: EditText, satisEdit: Spinner){
        // TODO コンストラクタ
        BookNameEdit = bookNameEdit
        TitleEdit = titleEdit
        CommentEdit = commentEdit
        SatisEdit = satisEdit

        SatisEdit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
    private var BookNameEdit : EditText? = null
        get(){
            return field
        }
    private var TitleEdit : EditText? = null
        get(){
            return field
        }
    private var CommentEdit : EditText? = null
        get(){
            return field
        }
    private var SatisEdit : Spinner? = null
        get(){
            return field
        }
    val BookNameString : String
        get(){
            if(BookNameEdit != null){   return BookNameEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val TitleString : String
        get(){
            if(TitleEdit != null){   return TitleEdit!!.text.toString()  }
            else{                       return ""                       }
        }
    val CommentString : String
        get(){
            if(CommentEdit != null){   return CommentEdit!!.text.toString()  }
            else{                       return ""                       }
        }

    var SatisString : String = "1"
        get(){          return field    }
        set(value){     field = value   }

    fun isEmpty(): Boolean {
        // TODO 空チェック
        return ( BookNameEdit?.text.toString().isEmpty() || TitleEdit?.text.toString().isEmpty() ||
                CommentEdit?.text.toString().isEmpty() )
    }

    fun checkBookName(max : Int) : Boolean{
        // TODO 書籍名チェック
        if(BookNameEdit != null){
            var len : Int = BookNameEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len, max) )
        }
        return false
    }

    fun checkTitle(max : Int) : Boolean{
        // TODO タイトルチェック
        if(TitleEdit != null){
            var len : Int = TitleEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len, max) )
        }
        return false
    }

    fun checkComment(max : Int) : Boolean{
        // TODO コメントチェック
        if(CommentEdit != null){
            var len : Int = CommentEdit!!.text.toString().length
            return ( DomainHelper.IsRange(len, max) )
        }
        return false
    }

}