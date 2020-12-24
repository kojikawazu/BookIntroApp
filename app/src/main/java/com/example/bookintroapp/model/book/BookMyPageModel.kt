package com.example.bookintroapp.model.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.model.base.ModelBase
import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookListForm2
import com.example.bookintroapp.valueobject.form.BookMyPageForm
import com.example.bookintroapp.valueobject.form.TitleForm
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

// マイページモデル
class BookMyPageModel : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // フォーム
    private var titleForm: TitleForm? = null
    private var bookMyPageForm: BookMyPageForm? = null
    private var bookListForm: BookListForm2? = null

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
        bookMyPageForm = BookMyPageForm(
            view.findViewById(R.id.bookmypage_followView),
            view.findViewById(R.id.bookmypage_followerView),
            view.findViewById(R.id.bookmypage_followButton)
        )
        bookListForm = BookListForm2(
            view.findViewById(R.id.bookmypage_listview)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag,_userRepository)

        // タイトル
        titleForm?.setTitle_mypage(userEntity!!.UserName)

        // フォロー数の更新
        bookMyPageForm?.Follow?.updateFollowView(userEntity!!)
        bookMyPageForm?.Follow?.updateFollowerView(userEntity!!)

        // データリストの設定
        setListView(view, frag)
    }

    private fun setListView(view: View, frag: Fragment){
        // TODO データリストの設定

        // リスト(ユーザ自身の書籍リストを選択)
        val tsk: Task<QuerySnapshot> = _bookRepository.select_byuserId(userEntity!!.UserId)
        _bookRepository.execing(tsk)
        val list: MutableList<BookEntity> = _bookRepository.getResultEntityList(tsk)

        // レイアウト追加処理
        bookListForm?.createChildLayout(frag, list)
    }


}
