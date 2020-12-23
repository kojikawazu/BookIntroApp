package com.example.bookintroapp.model.book

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.model.base.ModelBase
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookDetailForm
import com.example.bookintroapp.valueobject.form.ReplyListForm
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

// 書籍詳細モデル
class BookDetailModel : ModelBase() {

    // エンティティ
    private var bookEntity: BookEntity? = null
    private var userEntity: UserEntity? = null

    // フォーム
    private var bookDetailForm: BookDetailForm? = null
    private var replyListForm: ReplyListForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _replyRepository: IReplyRepository = ReplyRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO レイアウトの設定

        // 書籍詳細レイアウト
        bookDetailForm = BookDetailForm(
                view.findViewById(R.id.bookview_contents_textView),
                view.findViewById(R.id.bookview_book_edit),
                view.findViewById(R.id.bookview_bookcontents_edit),
                view.findViewById(R.id.bookview_satis_edit),
                view.findViewById(R.id.bookview_imageView),
                view.findViewById(R.id.bookview_niceCnt_textView),
                view.findViewById(R.id.bookview_markCnt_textView),
                view.findViewById(R.id.bookview_replyCnt_textView),
                view.findViewById(R.id.bookview_created_textView),
                view.findViewById(R.id.bookview_nice_button),
                view.findViewById(R.id.bookview_bookmark_button),
                view.findViewById(R.id.bookview_reply_button)
        )

        // 返信リストレイアウト
        replyListForm = ReplyListForm(
            view.findViewById(R.id.reply_list_layout)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナーの設定
        val ac: MainActivity = frag.activity as MainActivity
        val targetBookId = ac.getTargetBookId()

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag, _userRepository)

        // 書籍エンティティ取得
        bookEntity = FirebaseHelpler.selectBookEntity(targetBookId, _bookRepository)

        // 書籍投稿のユーザを取得
        val tsk: Task<DocumentSnapshot> = _userRepository.select_byId(bookEntity!!.UserId)
        _userRepository.execing(tsk)
        val bookUserEntity: UserEntity? = _userRepository.getResultEntityD(tsk)

        // 書籍データ反映
        bookDetailForm?.setData(bookUserEntity!!, bookEntity!!)

        // リプライリストの反映
        createReplyList(view, frag)

        // UI更新
        updateViewUI()

        // クリックリスナー
        bookDetailForm?.setOnButtonClickListener({
            // TODO いいね押下時
            OnNiceCntEventListener()
        }, {
            // TODO ブックマーク押下時
            OnBookMarkEventListener()
        }, {
            // TODO リプライ押下時
            OnReplyEventListener(frag)
        })
    }

    private fun createReplyList(view: View,frag: Fragment){
        // TODO リプライリストの生成 & 反映

        // リスト(書籍のリプライリストを選択)
        val tsk: Task<QuerySnapshot> = _replyRepository.select_bybookId(bookEntity!!.BookId)
        _replyRepository.execing(tsk)
        val list: MutableList<ReplyEntity> = _replyRepository.getResultEntityList(tsk)

        // レイアウト追加処理
        replyListForm?.createReplyLayout(frag, list)
    }

    private fun updateViewUI(){
        // TODO UIの更新
        if(userEntity == null || bookEntity == null)    return
        // 自身のユーザがいいね登録したかチェック
        bookDetailForm?.updateNiceCntButtonUI(userEntity!!, bookEntity!!)

        // 自身のユーザがブックマーク登録したかチェック
        bookDetailForm?.updateBookmarkButtonUI(userEntity!!, bookEntity!!)
    }

    private fun OnNiceCntEventListener(){
        // TODO いいね押下イベント
        if(userEntity == null || bookEntity == null)    return
        val ret = bookDetailForm?.NiceCntButton!!.OnNiceCntEventlistener(userEntity!!, bookEntity!!)
        if(ret) {
            // いいね追加に成功

            // いいねの合計を取得
            bookEntity?.setNiceCnt(bookDetailForm?.NiceCntButton!!.getNiceCntCount(bookEntity!!).toInt())
            // ビューに反映
            bookDetailForm?.setNiceText(bookEntity!!.NiceCntDisplay)
            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_niceCnt_byId(bookEntity!!.BookId, bookEntity!!.NiceCnt)
            _bookRepository.execing(tsk)

            // 自身のユーザがブックマーク登録したかチェック
            bookDetailForm?.updateNiceCntButtonUI(userEntity!!, bookEntity!!)
        }
    }

    private fun OnBookMarkEventListener(){
        // TODO ブックマーク押下イベント
        if(userEntity == null || bookEntity == null)    return
        val ret = bookDetailForm?.BookmarkButton!!.OnBookMarkEventListener(userEntity!!, bookEntity!!)
        if(ret) {
            // ブックマーク追加に成功

            // ブックマークの合計を取得
            bookEntity?.setMarkCnt(bookDetailForm?.BookmarkButton!!.getBookMarkCount(bookEntity!!).toInt())
            // ビューに反映
            bookDetailForm?.setMarkText(bookEntity!!.MarkCntDisplay)
            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_markCnt_byId(bookEntity!!.BookId, bookEntity!!.MarkCnt)
            _bookRepository.execing(tsk)

            // 自身のユーザがブックマーク登録したかチェック
            bookDetailForm?.updateBookmarkButtonUI(userEntity!!, bookEntity!!)
        }
    }

    private fun OnReplyEventListener(frag: Fragment){
        // TODO リプライボタン押下時処理

        // 書籍リプライ画面へ遷移
        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookreply)
    }
}