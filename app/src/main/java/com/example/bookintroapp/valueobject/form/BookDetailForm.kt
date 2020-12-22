package com.example.bookintroapp.valueobject.form

import android.opengl.Visibility
import android.view.View
import android.widget.*
import com.example.bookintroapp.R
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity

// 書籍詳細フォーム
class BookDetailForm() {

    constructor(bookTitleText: TextView, bookNameText: TextView,
                commentText: TextView, satisText: TextView, satisImage: ImageView,
                niceText: TextView, markText: TextView, replyText: TextView, created: TextView,
                niceCntButton: Button, bookmartButton: Button, replyButton: Button) : this(){
        // TODO コンストラクタ
        BookTitleText = bookTitleText
        BookNameText = bookNameText
        CommentText = commentText
        SatisText = satisText
        SatisImage = satisImage
        NiceText = niceText
        MarkText = markText
        ReplyText = replyText
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
    private var SatisImage : ImageView? = null
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
    private var ReplyText : TextView? = null
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
        this.MarkText?.text = entity.MarkCntDisplay
        this.ReplyText?.text = entity.ReplyCntDisplay
        this.Created?.text = entity.Created.toString()

        this.SatisText?.visibility = View.GONE
        when(entity.SatisCnt){
            1 -> {  this.SatisImage?.setImageResource(R.drawable.star_1) }
            2 -> {  this.SatisImage?.setImageResource(R.drawable.star_2) }
            3 -> {  this.SatisImage?.setImageResource(R.drawable.star_3) }
            4 -> {  this.SatisImage?.setImageResource(R.drawable.star_4) }
            5 -> {  this.SatisImage?.setImageResource(R.drawable.star_5) }
            else ->{
                this.SatisImage?.setImageResource(R.drawable.star_5)
            }
        }
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