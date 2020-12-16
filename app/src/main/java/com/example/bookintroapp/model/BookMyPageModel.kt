package com.example.bookintroapp.model

import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.valueobject.adapter.BookListAdapter
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookListForm
import com.example.bookintroapp.valueobject.form.TitleForm
import com.example.bookintroapp.view.fragment.BookMypageFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

// マイページモデル
class BookMyPageModel : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // フォーム
    private var titleForm: TitleForm? = null
    private var bookListForm: BookListForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
        titleForm = TitleForm(
            view.findViewById(R.id.bookmypage_contents_textView)
        )
        bookListForm = BookListForm(
            view.findViewById(R.id.bookmypage_listview)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = ActivityHelper.selectUserEntity(frag,_userRepository)

        // タイトル
        titleForm?.setTitle_mypage(userEntity!!.UserName)

        // データリストの設定
        setListView(view, frag)
    }

    private fun setListView(view: View, frag: Fragment){
        // TODO データリストの設定

        // リスト(ユーザ自身の書籍リストを選択)
        val tsk: Task<QuerySnapshot> = _bookRepository.select_byuserId(userEntity!!.UserId)
        _bookRepository.execing(tsk)

        // バインド処理
        val list: MutableList<BookEntity> = _bookRepository.getResultEntityList(tsk)
        bookListForm?.createAdapter(frag, list, userEntity!!)

        // リスナー設定
        bookListForm?.setOnItemClick(frag, R.id.action_bookmain_to_bookdetail)
    }


}
