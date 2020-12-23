package com.example.bookintroapp.model

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.FollowEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookListForm
import com.example.bookintroapp.valueobject.form.TitleForm
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

// 書籍メインモデル
class BookMainModel : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // フォーム
    private var titleForm: TitleForm? = null
    private var bookListForm: BookListForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _followRepository: IFollowRepository = FollowRepository()

    override fun setLayout(view: View) {
        // TODO ビューの設定
        titleForm = TitleForm(
                view.findViewById(R.id.bookmain_contents_textView)
        )
        bookListForm = BookListForm(
                view.findViewById(R.id.bookmain_listview)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO リスナー設定

        // 書籍追加ボタンの反映
        val ac : MainActivity = frag.activity as MainActivity
        ac.setBookListener(frag)

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag,_userRepository)

        // タイトル
        titleForm?.setTitle_bookmain(userEntity!!.UserName)

        // データリストの設定
        setListView(view, frag)
    }

    private fun setListView(view: View, frag: Fragment) {
        // TODO データリストの設定

        // リスト(ユーザ自身のフォローリストを選択)
        val folTsk: Task<QuerySnapshot> = _followRepository.select_byuserId(userEntity!!.UserId)
        _followRepository.execing(folTsk)
        val followList: MutableList<FollowEntity> = _followRepository.getResultEntityList(folTsk)

        // フォローリストからユーザIDによる書籍取得
        val bookList: MutableList<BookEntity> = mutableListOf()
        for(followEntity: FollowEntity in followList){
            val bookTsk: Task<QuerySnapshot> =  _bookRepository.select_byuserId(followEntity.FollowerId)
            _bookRepository.execing(bookTsk)
            val selectList: MutableList<BookEntity> = _bookRepository.getResultEntityList(bookTsk)
            for(selectEntity in selectList){
                bookList.add(selectEntity)
            }
            selectList.clear()
        }

        // バインド処理
        bookListForm?.createAdapter(frag, bookList, userEntity!!)

        // リスナー設定
        bookListForm?.setOnItemClick(frag, R.id.action_bookmain_to_bookdetail)

    }

}