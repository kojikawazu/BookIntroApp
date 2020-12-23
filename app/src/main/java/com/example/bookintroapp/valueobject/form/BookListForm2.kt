package com.example.bookintroapp.valueobject.form

import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.button.NiceCntReplyButton
import com.example.bookintroapp.valueobject.entity.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// 書籍リストフォーム
class BookListForm2() {

    // エンティティ
    private var userEntity: UserEntity? = null

    // レイアウト
    private val niceCntButton: NiceCntButton = NiceCntButton()
    private val bookmarkButton: BookmarkButton = BookmarkButton()


    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()

    constructor(layout: LinearLayout) : this() {
        // TODO コンストラクタ
        ParentLayout = layout
    }

    // getter
    private var ParentLayout: LinearLayout? = null
        get() {
            return field
        }

    fun createChildLayout(frag: Fragment, list: MutableList<BookEntity>) {
        // TODO 返信リストの追加生成
        val ac: MainActivity = frag.activity as MainActivity

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag, _userRepository)

        for (entity in list) {
            // 子レイアウト生成
            val layout: LinearLayout = ac.layoutInflater.inflate(R.layout.list_book_layout, null) as LinearLayout

            // 部品検索

            // ラベル
            val userView: TextView = layout.findViewById(R.id.booklist_userName)
            val bookView: TextView = layout.findViewById(R.id.booklist_bookName)
            val titleView: TextView = layout.findViewById(R.id.booklist_title)
            val satisView: TextView = layout.findViewById(R.id.booklist_satis)
            val satisImage: ImageView = layout.findViewById(R.id.booklist_imageview)
            val commentView: TextView = layout.findViewById(R.id.booklist_comment)
            val createdView: TextView = layout.findViewById(R.id.booklist_created)
            val niceCntView: TextView = layout.findViewById(R.id.booklist_niceCnt)
            val markView: TextView = layout.findViewById(R.id.booklist_markCnt)
            val replyView: TextView = layout.findViewById(R.id.booklist_replyCnt)

            // ボタン
            val niceCntButton: Button = layout.findViewById(R.id.button_niceCnt)
            val markButton: Button = layout.findViewById(R.id.button_markCnt)
            val replyButton: Button = layout.findViewById(R.id.button_reply)

            // 値を反映
            titleView.text = entity.BookTitle
            bookView.text = entity.BookName
            satisView.text = entity.SatisCntDisplay
            commentView.text = entity.Comment
            createdView.text = entity.Created.toString()
            niceCntView.text = entity.NiceCntDisplay
            markView.text = entity.MarkCntDisplay
            replyView.text = entity.ReplyCntDisplay

            // 満足度イメージの設定
            ActivityHelper.setImage_satisfaction(satisView, satisImage, entity.SatisCnt)

            // ユーザ名の反映
            setUserName(userView, entity)

            // UI更新
            updateNicecntUI(niceCntButton, entity)
            updateMarkUI(markButton, entity)

            // イベントリスナー設定
            niceCntButton.setOnClickListener { _ ->
                // TODO いいねボタン押下時
                OnNiceCntClickListener(niceCntView, niceCntButton, entity)
            }
            markButton.setOnClickListener { _ ->
                // TODO ブックマークボタン押下時
                OnBookmarkClickListener(markView, markButton, entity)
            }
            replyButton.setOnClickListener {
                // TODO リプライボタン押下時
                OnReplyClickListener(frag, entity)
            }

            layout.setOnClickListener(){
                // TODO レイアウト全体をタッチ処理

                // ターゲット書籍IDをアクティビティに保存
                val ac: MainActivity = frag.activity as MainActivity
                ac.saveTargetBookId(entity.BookId)

                // 書籍詳細画面へ遷移
                ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookdetail)
            }

            // 親レイアウトに追加
            ParentLayout?.addView(layout)
        }
    }

    private fun updateNicecntUI(button: Button, bookEntity: BookEntity) {
        // TODO いいねボタンのUI更新
        button.isEnabled = niceCntButton.isNiceCnt_byUser(userEntity!!, bookEntity)
    }

    private fun updateMarkUI(button: Button, bookEntity: BookEntity) {
        // TODO ブックマークボタンのUI更新
        button.isEnabled = bookmarkButton.isBookMark_byUser(userEntity!!, bookEntity)
    }

    private fun setUserName(userView: TextView, entity: BookEntity){
        // TODO ユーザIDによりユーザ名の反映
        val tsk: Task<DocumentSnapshot> =  _userRepository.select_byId(entity.UserId)
        _userRepository.execing(tsk)
        if(_userRepository.isSuccessed(tsk)){
            // ユーザエンティティ取得に成功
            val user: UserEntity? = _userRepository.getResultEntityD(tsk)
            if(user != null){
                userView.text = user.UserName
            }
        }
    }

    private fun OnNiceCntClickListener(niceCntView: TextView, button: Button, bookEntity: BookEntity){
        // TODO いいねボタン押下処理
        if(userEntity == null)    return

        // いいねデータ追加
        val ret = niceCntButton.OnNiceCntEventlistener(userEntity!!, bookEntity)
        if(ret){
            // 追加成功

            // いいね数を更新
            bookEntity.setNiceCnt(niceCntButton.getNiceCntCount(bookEntity).toInt())
            // ビューに反映
            niceCntView.text = bookEntity.NiceCntDisplay

            // 書籍テーブルのいいねカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_niceCnt_byId(bookEntity.BookId, bookEntity.NiceCnt)
            _bookRepository.execing(tsk)

            // UI更新
            updateNicecntUI(button, bookEntity)
        }
    }

    private fun OnBookmarkClickListener(markView: TextView, button: Button, bookEntity: BookEntity){
        // TODO ブックマーク押下処理

        // ブックマークリストに追加
        val ret = bookmarkButton.OnBookMarkEventListener(userEntity!!, bookEntity)
        if(ret){
            // ブックマーク追加成功

            // ブックマークの合計を取得
            bookEntity.setMarkCnt(bookmarkButton.getBookMarkCount(bookEntity).toInt())
            // ビューに反映
            markView.text = bookEntity.MarkCntDisplay

            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_markCnt_byId(bookEntity.BookId, bookEntity.MarkCnt)
            _bookRepository.execing(tsk)

            // UI更新
            updateMarkUI(button, bookEntity)
        }
    }

    private fun OnReplyClickListener(frag: Fragment, bookEntity: BookEntity){
        // TODO リプライボタン押下時処理
        // 書籍リプライ画面へ遷移

        // 書籍IDをアクティビティに保存
        val ac: MainActivity = frag.activity as MainActivity
        ac.saveTargetBookId(bookEntity.BookId)

        // 書籍詳細へ遷移
        ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookreply)
    }
}