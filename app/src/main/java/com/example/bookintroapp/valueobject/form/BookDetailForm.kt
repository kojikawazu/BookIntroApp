package com.example.bookintroapp.valueobject.form

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity

// 書籍詳細フォーム
class BookDetailForm() {

    constructor(bookTitleText: TextView, bookNameText: TextView,
                commentText: TextView, satisText: TextView,
                niceText: TextView, markText: TextView, created: TextView,
                niceCntButton: Button, bookmartButton: Button, replyButton: Button) : this(){
        // TODO コンストラクタ
        BookTitleText = bookTitleText
        BookNameText = bookNameText
        CommentText = commentText
        SatisText = satisText
        NiceText = niceText
        MarkText = markText
        Created = created
        NiceCntButtonS = niceCntButton
        BookMarkButtonS = bookmartButton
        ReplyButtonS = replyButton
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
    private var NiceCntButtonS: Button? = null
        get(){
            return field
        }
    private var BookMarkButtonS: Button? = null
        get(){
            return field
        }

    private var ReplyButtonS: Button? = null
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
    var NiceCntButton: NiceCntButton = NiceCntButton()
        get(){
            return field
        }
    var BookmarkButton: BookmarkButton = BookmarkButton()
        get(){
            return field
        }


    fun setNiceText(niceCnt: String){
        // TODO いいねリストの更新
        NiceText?.text = niceCnt
    }

    fun setMarkText(markCnt: String){
        // TODO ブックマークリストの更新
        MarkText?.text = markCnt
    }

    fun setData(entity: BookEntity){
        // TODO データ反映
        this.BookTitleText?.text = entity.BookTitle
        this.BookNameText?.text = entity.BookTitle
        this.CommentText?.text = entity.Comment
        this.SatisText?.text = entity.SatisCntDisplay
        this.NiceText?.text = entity.NiceCntDisplay
        this.MarkText?.text = entity.NiceCntDisplay
        this.Created?.text = entity.Created.toString()
    }

    fun setOnButtonClickListener(niceCntFunc: () -> Unit, bookmarkFunc: () -> Unit, replyFunc: () -> Unit){
        // TODO ボタンリスナーの設定
        NiceCntButtonS?.apply {
            setOnClickListener {
                // TODO いいね押下時
                niceCntFunc()
            }
        }
        BookMarkButtonS?.apply {
            setOnClickListener {
                // TODO ブックマーク押下時
                bookmarkFunc()
            }
        }
        ReplyButtonS?.apply {
            setOnClickListener{
                // TODO リプライ押下時
                replyFunc()
            }
        }
    }

    fun updateNiceCntButtonUI(userEntity: UserEntity, bookEntity: BookEntity){
        // TODO いいねボタンの更新
        NiceCntButtonS?.isEnabled = NiceCntButton.isNiceCnt_byUser(userEntity, bookEntity)
    }

    fun updateBookmarkButtonUI(userEntity: UserEntity, bookEntity: BookEntity){
        // TODO ブックマークボタンの更新
        BookMarkButtonS?.isEnabled = BookmarkButton.isBookMark_byUser(userEntity, bookEntity)
    }



}