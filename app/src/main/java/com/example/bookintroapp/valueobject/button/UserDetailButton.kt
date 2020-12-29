package com.example.bookintroapp.valueobject.button

import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.valueobject.entity.BookEntity

// 別ユーザボタン
class UserDetailButton {

    fun OnClickListener(frag: Fragment, bookEntity: BookEntity){
        // TODO 別ユーザ画面へ遷移

        // フォロワーIDをアクティビティに保存
        val ac: MainActivity = frag.activity as MainActivity
        ac.saveTargetFollowerId(bookEntity.UserId)

        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookfollower)
    }
}