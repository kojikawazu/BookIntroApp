package com.example.bookintroapp.model.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.model.base.ModelBase
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.FollowEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.book.BookMyPageForm
import com.example.bookintroapp.valueobject.form.common.TitleForm
import com.example.bookintroapp.valueobject.form.list.BookListForm2
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// フォロワーモデル
class BookFollowerModel : ModelBase() {

    // エンティティ
    private var userEntity: UserEntity? = null
    private var followerEntity: UserEntity? = null

    // フォーム
    private var titleForm: TitleForm? = null
    private var bookMyPageForm: BookMyPageForm? = null
    private var bookListForm: BookListForm2? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _followRepository: IFollowRepository = FollowRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
        titleForm = TitleForm(
            view.findViewById(R.id.bookfollower_contents_textView)
        )

        bookMyPageForm = BookMyPageForm(
            view.findViewById(R.id.bookfollower_followView),
            view.findViewById(R.id.bookfollower_followerView),
            view.findViewById(R.id.bookfollower_followButton),
        )

        bookListForm = BookListForm2(
            view.findViewById(R.id.bookfollower_listview)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag, _userRepository)

        // 選択ユーザのエンティティ取得
        followerEntity = FirebaseHelpler.selectSelectUserEntity(frag, _userRepository)

        // タイトル設定
        if(followerEntity != null) {
            titleForm?.setTitle_follower(followerEntity!!.UserName)
        }

        // フォロー数の更新
        updateFollowViewUI()

        // イベントリスナー
        bookMyPageForm?.FollowButton?.setOnClickListener{
            onClickFollowListener()
        }

        // ビューリストを設定
        setListView(frag)
    }

    private fun setListView(frag: Fragment){
        // TODO データリストの設定
        if(followerEntity == null)  return

        // リスト(ユーザ自身の書籍リストを選択)
        val tsk: Task<QuerySnapshot> = _bookRepository.select_byuserId(followerEntity!!.UserId)
        _bookRepository.execing(tsk)
        val list: MutableList<BookEntity> = _bookRepository.getResultEntityList(tsk)

        // レイアウト追加処理
        bookListForm?.createChildLayout(frag, list)
    }

    private fun updateFollowViewUI(){
        // TODO フォロービューの更新
        if(followerEntity == null || userEntity == null)  return

        // フォロー数の更新
        bookMyPageForm?.Follow?.updateFollowView(followerEntity!!)
        bookMyPageForm?.Follow?.updateFollowerView(followerEntity!!)

        bookMyPageForm?.updateButtonUI(userEntity!!, followerEntity!!)
    }

    private fun onClickFollowListener(){
        // TODO クリックイベントリスナー
        if(userEntity == null || followerEntity == null)  return

        // フォローエンティティを追加
        val entity = FollowEntity(userEntity!!, followerEntity!!, Date())
        val tsk: Task<DocumentReference> = _followRepository.insert(entity)
        _followRepository.execing(tsk)

        // フォロー数の更新
        updateFollowViewUI()
    }

}