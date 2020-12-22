package com.example.bookintroapp.model

import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.adapter.BookListAdapter
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookListForm
import com.example.bookintroapp.valueobject.form.TitleForm
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

// ブックマークモデル
class BookMarkModel  : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // フォーム
    private var titleForm: TitleForm? = null
    private var bookListForm: BookListForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _markRepository: IMarkRepository = MarkRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
        titleForm = TitleForm(
            view.findViewById(R.id.bookmark_contents_textView)
        )
        bookListForm = BookListForm(
            view.findViewById(R.id.bookmark_listview)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag,_userRepository)

        // タイトル
        titleForm?.setTitle_bookmark(userEntity!!.UserName)

        // データリストの設定
        setListView(view, frag)
    }

    private fun setListView(view: View, frag: Fragment){
        // TODO データリストの設定

        // ブックマークリストの取得
        val markTsk: Task<QuerySnapshot> = _markRepository.select_byuserId(userEntity!!.UserId)
        _markRepository.execing(markTsk)
        val markList: MutableList<MarkEntity> = _markRepository.getResultEntityList(markTsk)

        // ブックマークリストから書籍取得
        val bookList: MutableList<BookEntity> = mutableListOf()
        for(markEntity: MarkEntity in markList){
            val bookTsk: Task<DocumentSnapshot> =  _bookRepository.select_byId(markEntity.BookId)
            _bookRepository.execing(bookTsk)
            val bookEntity: BookEntity? = _bookRepository.getResultEntity(bookTsk)
            if(bookEntity != null){
                bookList.add(bookEntity)
            }
        }

        // バインド処理
        bookListForm?.createAdapter(frag, bookList, userEntity!!)

        // リスナー設定
        bookListForm?.setOnItemClick(frag, R.id.action_bookmain_to_bookdetail)
    }
}