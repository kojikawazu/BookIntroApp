package com.example.bookintroapp.valueobject.form

import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

// 書籍詳細フォーム
class BookDetailForm() {

    constructor(bookTitleText: TextView, bookNameText: TextView,
                commentText: TextView, satisText: TextView, niceText: TextView,
                markText: TextView, created: TextView) : this(){
        BookTitleText = bookTitleText
        BookNameText = bookNameText
        CommentText = commentText
        SatisText = satisText
        NiceText = niceText
        MarkText = markText
        Created = created
    }

    // getter
    private var BookTitleText : TextView? = null
        get(){
            return field
        }
    private var BookNameText : TextView? = null
        get(){
            return field
        }
    private var CommentText : TextView? = null
        get(){
            return field
        }
    private var SatisText : TextView? = null
        get(){
            return field
        }
    private var NiceText : TextView? = null
        get(){
            return field
        }
    private var MarkText : TextView? = null
        get(){
            return field
        }
    private var Created : TextView? = null
        get(){
            return field
        }
    val BookTitleString : String
        get(){
            if(BookTitleText != null){   return BookTitleText!!.text.toString()  }
            else{                       return ""                       }
        }
    val BookNameString : String
        get(){
            if(BookNameText != null){   return BookNameText!!.text.toString()  }
            else{                       return ""                       }
        }
    val CommentString : String
        get(){
            if(CommentText != null){   return CommentText!!.text.toString()  }
            else{                       return ""                       }
        }
    val SatisString : String
        get(){
            if(SatisText != null){   return SatisText!!.text.toString()  }
            else{                       return ""                       }
        }
    val NiceString : String
        get(){
            if(NiceText != null){   return NiceText!!.text.toString()  }
            else{                       return ""                       }
        }

    fun setNiceText(niceCnt: String){
        // TODO いいねリストの更新
        NiceText?.text = niceCnt
    }

    fun setMarkText(markCnt: String){
        // TODO ブックマークリストの更新
        MarkText?.text = markCnt
    }

    fun setData(title: String, bookName: String, comment: String, satisCnt: String, niceCnt: String, markCnt: String, created: String){
        // TODO データ反映
        this.BookTitleText?.text = title
        this.BookNameText?.text = bookName
        this.CommentText?.text = comment
        this.SatisText?.text = satisCnt
        this.NiceText?.text = niceCnt
        this.MarkText?.text = markCnt
        this.Created?.text = created
    }



}