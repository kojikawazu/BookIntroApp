package com.example.bookintroapp.valueobject.button

import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.valueobject.entity.BookEntity

// 書籍リストをボタン化
class BookDetailButton {

    fun OnClickListener(frag: Fragment, bookEntity: BookEntity){
        // TODO 詳細情報へ画面遷移

        // ターゲット書籍IDをアクティビティに保存
        val ac: MainActivity = frag.activity as MainActivity
        ac.saveTargetBookId(bookEntity.BookId)

        // 書籍詳細画面へ遷移
        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookdetail)
    }
}