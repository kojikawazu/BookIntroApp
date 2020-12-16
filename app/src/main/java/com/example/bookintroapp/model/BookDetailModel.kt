package com.example.bookintroapp.model

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.adapter.ViewHolder
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookDetailForm
import com.google.android.gms.tasks.Task

// 書籍詳細モデル
class BookDetailModel : ModelBase() {

    // エンティティ
    private var bookEntity: BookEntity? = null
    private var userEntity: UserEntity? = null

    // フォーム
    private var bookMarkButtonS: Button? = null
    private var niceCntButtonS: Button? = null

    private var bookDetailForm: BookDetailForm? = null
    private var bookmarkButton: BookmarkButton? = null
    private var niceCntButton: NiceCntButton? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private var _bookRepository: IBookRepository = BookRepository()
    private val _niceRepository: INiceRepository = NiceRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO レイアウトの設定
        bookDetailForm = BookDetailForm(
            view.findViewById(R.id.bookview_contents_textView),
            view.findViewById(R.id.bookview_book_edit),
            view.findViewById(R.id.bookview_bookcontents_edit),
            view.findViewById(R.id.bookview_satis_edit),
            view.findViewById(R.id.bookview_niceCnt_textView),
            view.findViewById(R.id.bookview_markCnt_textView),
            view.findViewById(R.id.bookview_created_textView)
        )

        // ボタン
        bookMarkButtonS = view.findViewById(R.id.bookview_bookmark_button)
        bookmarkButton = BookmarkButton()
        niceCntButtonS = view.findViewById(R.id.bookview_nice_button)
        niceCntButton = NiceCntButton()
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナーの設定
        val ac: MainActivity = frag.activity as MainActivity
        val targetBookId = ac.getTargetBookId()

        // ユーザエンティティ取得
        userEntity = ActivityHelper.selectUserEntity(frag, _userRepository)

        // 書籍エンティティ取得
        bookEntity = ActivityHelper.selectBookEntity(targetBookId, _bookRepository)

        // データ反映
        bookDetailForm?.setData(bookEntity!!.BookTitle, bookEntity!!.BookTitle, bookEntity!!.Comment,
                                bookEntity!!.SatisCntDisplay, bookEntity!!.NiceCntDisplay,
                                bookEntity!!.NiceCntDisplay, bookEntity!!.Created.toString())

        // UI更新
        updateViewUI()

        // クリックリスナー
        niceCntButtonS?.apply {
            setOnClickListener {
                // TODO いいね押下時
                OnNiceCntEventListener()
            }
        }
        bookMarkButtonS?.apply {
            setOnClickListener {
                // TODO ブックマーク押下時
                OnBookMarkEventListener()
            }
        }
    }

    private fun updateViewUI(){
        // TODO UIの更新

        // 自身のユーザがいいね登録したかチェック
        niceCntButtonS?.isEnabled = niceCntButton!!.isNiceCnt_byUser(userEntity!!, bookEntity!!)

        // 自身のユーザがブックマーク登録したかチェック
        bookMarkButtonS?.isEnabled = bookmarkButton!!.isBookMark_byUser(userEntity!!, bookEntity!!)
    }

    private fun OnNiceCntEventListener(){
        // TODO いいね押下イベント
        val ret = niceCntButton!!.OnNiceCntEventlistener(userEntity!!, bookEntity!!)
        if(ret) {
            // いいね追加に成功

            // いいねの合計を取得
            bookEntity?.setNiceCnt(niceCntButton!!.getNiceCntCount(bookEntity!!).toInt())
            // ビューに反映
            bookDetailForm?.setNiceText(bookEntity!!.NiceCntDisplay)
            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_niceCnt_byId(bookEntity!!.BookId, bookEntity!!.NiceCnt)
            _bookRepository.execing(tsk)

            // 自身のユーザがブックマーク登録したかチェック
            niceCntButtonS?.isEnabled = niceCntButton!!.isNiceCnt_byUser(userEntity!!, bookEntity!!)
        }
    }

    private fun OnBookMarkEventListener(){
        // TODO ブックマーク押下イベント
        val ret = bookmarkButton!!.OnBookMarkEventListener(userEntity!!, bookEntity!!)
        if(ret) {
            // ブックマーク追加に成功

            // ブックマークの合計を取得
            bookEntity?.setMarkCnt(bookmarkButton!!.getBookMarkCount(bookEntity!!).toInt())
            // ビューに反映
            bookDetailForm?.setMarkText(bookEntity!!.MarkCntDisplay)
            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_markCnt_byId(bookEntity!!.BookId, bookEntity!!.MarkCnt)
            _bookRepository.execing(tsk)

            // 自身のユーザがブックマーク登録したかチェック
            bookMarkButtonS?.isEnabled = bookmarkButton!!.isBookMark_byUser(userEntity!!, bookEntity!!)
        }
    }
}