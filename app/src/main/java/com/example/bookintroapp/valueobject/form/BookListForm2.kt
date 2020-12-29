package com.example.bookintroapp.valueobject.form

import android.util.Log
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
import com.example.bookintroapp.valueobject.button.ReplyButton
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
    private val replyButton: ReplyButton = ReplyButton()

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()

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
            val niceButton: Button = layout.findViewById(R.id.button_niceCnt)
            val markButton: Button = layout.findViewById(R.id.button_markCnt)
            val replyButtonS: Button = layout.findViewById(R.id.button_reply)

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
            niceCntButton.updateNiceCntButton(niceButton, userEntity!!, entity)
            bookmarkButton.updateMarkButton(markButton, userEntity!!, entity)

            userView.isClickable = true
            userView.setOnClickListener{
                // 書籍詳細へ遷移
                ActivityHelper.nextFragment(frag, R.id.action_bookmain_to_bookfollower)
            }

            // イベントリスナー設定
            niceButton.setOnClickListener { _ ->
                // TODO いいねボタン押下時
                niceCntButton.OnNiceCntEventlistener(niceCntView, niceButton, userEntity!!, entity)
            }
            markButton.setOnClickListener { _ ->
                // TODO ブックマークボタン押下時
                bookmarkButton.OnBookMarkEventListener(markView, markButton, userEntity!!, entity)
            }
            replyButtonS.setOnClickListener {
                // TODO リプライボタン押下時
                replyButton.OnReplyClickListener(frag, entity)
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