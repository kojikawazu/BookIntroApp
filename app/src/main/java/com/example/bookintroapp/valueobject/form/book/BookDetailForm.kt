package com.example.bookintroapp.valueobject.form.book

import android.widget.*
import androidx.fragment.app.Fragment
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.button.ReplyButton
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
    private var niceCntButton: NiceCntButton = NiceCntButton()
        get(){
            return field
        }
        private set
    private var bookmarkButton: BookmarkButton = BookmarkButton()
        get(){
            return field
        }
        private set
    private var replyButton: ReplyButton = ReplyButton()
        get(){
            return field
        }
        private set

    fun setNiceText(niceCnt: String){
        // TODO いいねリストの更新
        NiceText?.text = niceCnt
    }

    fun setMarkText(markCnt: String){
        // TODO ブックマークリストの更新
        MarkText?.text = markCnt
    }

    fun setData(userEntity: UserEntity, entity: BookEntity){
        // TODO データ反映
        this.BookTitleText?.text = userEntity.UserName + "さんの投稿"
        this.BookNameText?.text = entity.BookName
        this.CommentText?.text = entity.Comment
        this.SatisText?.text = entity.SatisCntDisplay
        this.NiceText?.text = entity.NiceCntDisplay
        this.MarkText?.text = entity.MarkCntDisplay
        this.ReplyText?.text = entity.ReplyCntDisplay
        this.Created?.text = entity.Created.toString()

        // 満足度イメージの設定
        setSatis(entity.SatisCnt)
    }

    fun setOnButtonClickListener(userEntity: UserEntity, bookEntity: BookEntity, frag: Fragment){
        // TODO ボタンリスナーの設定
        NiceCntButtonS?.apply {
            setOnClickListener {
                // TODO いいね押下時
                niceCntButton.OnNiceCntEventlistener(NiceText!!, NiceCntButtonS!!, userEntity, bookEntity)
            }
        }
        BookMarkButtonS?.apply {
            setOnClickListener {
                // TODO ブックマーク押下時
                bookmarkButton.OnBookMarkEventListener(MarkText!!, BookMarkButtonS!!, userEntity, bookEntity)
            }
        }
        ReplyButtonS?.apply {
            setOnClickListener{
                // TODO リプライ押下時
                replyButton.OnReplyClickListener(frag)
            }
        }
    }

    fun updateNiceCntButtonUI(userEntity: UserEntity, bookEntity: BookEntity){
        // TODO いいねボタンの更新
        niceCntButton.updateNiceCntButton(NiceCntButtonS!!, userEntity, bookEntity)
    }

    fun updateBookmarkButtonUI(userEntity: UserEntity, bookEntity: BookEntity){
        // TODO ブックマークボタンの更新
        bookmarkButton.updateMarkButton(BookMarkButtonS!!, userEntity, bookEntity)
    }

    private fun setSatis(satisCnt: Int){
        // TODO 満足度イメージの設定
        ActivityHelper.setImage_satisfaction( SatisText!!, SatisImage!!, satisCnt)
    }

}