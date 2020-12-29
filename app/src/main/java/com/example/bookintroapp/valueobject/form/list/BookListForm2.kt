package com.example.bookintroapp.valueobject.form.list

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
import com.example.bookintroapp.valueobject.button.*
import com.example.bookintroapp.valueobject.entity.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

// 書籍リストフォーム
class BookListForm2() {

    // エンティティ
    private var userEntity: UserEntity? = null

    // レイアウト
    private val niceCntButton: NiceCntButton = NiceCntButton()
    private val bookmarkButton: BookmarkButton = BookmarkButton()
    private val replyButton: ReplyButton = ReplyButton()
    private val bookDetailButton: BookDetailButton = BookDetailButton()
    private val userDetailButton: UserDetailButton = UserDetailButton()

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
            val niceButtonS: Button = layout.findViewById(R.id.button_niceCnt)
            val markButtonS: Button = layout.findViewById(R.id.button_markCnt)
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
            niceCntButton.updateNiceCntButton(niceButtonS, userEntity!!, entity)
            bookmarkButton.updateMarkButton(markButtonS, userEntity!!, entity)

            userView.isClickable = true
            userView.setOnClickListener{
                // 書籍詳細へ遷移
                userDetailButton.OnClickListener(frag, entity)
            }

            // イベントリスナー設定
            niceButtonS.setOnClickListener { _ ->
                // TODO いいねボタン押下時
                niceCntButton.OnNiceCntEventlistener(niceCntView, niceButtonS, userEntity!!, entity)
            }
            markButtonS.setOnClickListener { _ ->
                // TODO ブックマークボタン押下時
                bookmarkButton.OnBookMarkEventListener(markView, markButtonS, userEntity!!, entity)
            }
            replyButtonS.setOnClickListener {
                // TODO リプライボタン押下時
                replyButton.OnReplyClickListener(frag, entity)
            }

            layout.setOnClickListener(){
                // TODO レイアウト全体をタッチ処理
                bookDetailButton.OnClickListener(frag, entity)
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
}