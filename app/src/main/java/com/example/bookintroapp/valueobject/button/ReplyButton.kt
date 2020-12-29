package com.example.bookintroapp.valueobject.button

import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.valueobject.entity.BookEntity

class ReplyButton {

    fun OnReplyClickListener(frag: Fragment){
        // TODO リプライボタン押下時処理

        // 書籍リプライ画面へ遷移
        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookreply)
    }

    fun OnReplyClickListener(frag: Fragment, bookEntity: BookEntity){
        // TODO リプライボタン押下時処理
        // 書籍リプライ画面へ遷移

        // 書籍IDをアクティビティに保存
        val ac: MainActivity = frag.activity as MainActivity
        ac.saveTargetBookId(bookEntity.BookId)

        // 書籍詳細へ遷移
        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookreply)
    }


}