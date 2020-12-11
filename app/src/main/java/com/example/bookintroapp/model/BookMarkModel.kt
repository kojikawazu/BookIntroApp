package com.example.bookintroapp.model

import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.adapter.BookListAdapter
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

// ブックマークモデル
class BookMarkModel  : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _markRepository: IMarkRepository = MarkRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = ActivityHelper.selectUserEntity(frag,_userRepository)

        // タイトル
        val titleView: TextView = view.findViewById(R.id.bookmark_contents_textView)
        titleView.text = userEntity?.UserName + "さんのブックマークリスト"

        // データリストの設定
        setListView(view, frag)
    }

    private fun setListView(view: View, frag: Fragment){
        // TODO データリストの設定

        // ブックマークリストの取得
        val markTsk: Task<QuerySnapshot> = _markRepository.select_byuserId(userEntity!!.UserId)
        while(!markTsk.isComplete){}
        val markList: MutableList<MarkEntity> = _markRepository.getResultEntityList(markTsk)

        // ブックマークリストから書籍取得
        val bookList: MutableList<BookEntity> = mutableListOf()
        for(markEntity: MarkEntity in markList){
            val bookTsk: Task<DocumentSnapshot> =  _bookRepository.select_byId(markEntity.BookId)
            while(!bookTsk.isComplete){ }
            val bookEntity: BookEntity? = _bookRepository.getResultEntity(bookTsk)
            if(bookEntity != null){
                bookList.add(bookEntity)
            }
        }

        // バインド処理
        val adapter: BookListAdapter = BookListAdapter(frag.requireContext(), R.layout.list_book_layout).apply {
            // 書籍データ
            for(entity in bookList){
                add(entity)
            }
            // サインインユーザデータ
            setUser(userEntity!!)
        }

        // ビューに反映
        val listView: ListView = view.findViewById(R.id.bookmark_listview)
        listView.adapter = adapter

    }
}