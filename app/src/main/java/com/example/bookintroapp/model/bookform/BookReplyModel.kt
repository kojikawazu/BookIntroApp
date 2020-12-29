package com.example.bookintroapp.model.bookform

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
import com.example.bookintroapp.valueobject.form.form.BookReplyForm
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// リプライモデル
class BookReplyModel : ModelBase() {

    // エンティティ
    private var bookEntity: BookEntity? = null
    private var userEntity: UserEntity? = null
    private var replyUserEntity: UserEntity? = null

    // フォーム
    private var bookReplyForm: BookReplyForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()
    private val _replyRepository: IReplyRepository = ReplyRepository()

    override fun setLayout(view: View) {
        // TODO レイアウトの設定
        bookReplyForm = BookReplyForm(
                view.findViewById(R.id.bookreply_contents_textView),
                view.findViewById(R.id.bookreply_book_edit),
                view.findViewById(R.id.bookreply_booktitle_edit),
                view.findViewById(R.id.bookreply_replycontents_edit),
                view.findViewById(R.id.bookreply_satis_spin),
                view.findViewById(R.id.bookreply_button)
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

        // 書籍投稿したユーザを取得
        if(bookEntity != null) {
            replyUserEntity = FirebaseHelpler.selectUserEntity(bookEntity!!.UserId, _userRepository)
        }

        // データの設定
        if(replyUserEntity != null && bookEntity != null){
            bookReplyForm?.setData(replyUserEntity!!,bookEntity!!)
        }

        // クリックリスナーの設定
        bookReplyForm?.setOnClickListener {
            // TODO リプライタップ
            onClickListener_reply(frag)
        }
    }

    fun onClickListener_reply(frag: Fragment){
        // TODO 返信ボタン押下時処理
        if(bookEntity == null || replyUserEntity == null)   return

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        val isError : Boolean = ActivityHelper.checkValidate(frag) { isValidate(frag) }
        // エラーチェック
        if( !isError ){
            return
        }

        // Firebaseへデータ登録処理
        // ----------------------------------------------------------------------------------------
        val entityNew = ReplyEntity(replyUserEntity!!, bookEntity!!, bookReplyForm!!, Date())
        val tskAdd: Task<DocumentReference> = _replyRepository.insert(entityNew)
        _replyRepository.execing(tskAdd)
        if( !_replyRepository.isSuccessed(tskAdd) ){
            // 追加に失敗
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(frag, R.string.book_reply_error_dialog)
            return
        }

        // リプライ回数を変更
        val tsk: Task<QuerySnapshot> = _replyRepository.select_bybookId(bookEntity!!.BookId)
        _replyRepository.execing(tsk)
        if( _replyRepository.isSuccessed(tsk) ){
            // 回数を取得
            val cnt: Int = _replyRepository.getResultEntiryCount(tsk)
            // リプライカウンタの更新
            val tskupd: Task<Void> = _bookRepository.update_replyCnt_byId(bookEntity!!.BookId, cnt)
            _bookRepository.execing(tskupd)
        }

        // 返信登録完了
        // ----------------------------------------------------------------------------------------
        // 返信成功ダイアログ
        ActivityHelper.show_success_dialog(frag,
                R.string.book_add_success_title, R.string.book_reply_success_contents) {
            // OKの場合、サインアップ画面閉じる
            ActivityHelper.backFragment(frag)
        }
    }

    fun isValidate(flag: Fragment) : String {
        // TODO バリデーションチェック
        // 空チェック
        if (bookReplyForm!!.isEmpty()) {
            return ActivityHelper.getStringDefine(flag, R.string.error_form_empty)
        }

        // コメントチェック
        var max : Int = ActivityHelper.getIntDefine(flag, R.string.bookreply_comment_len)
        if( !bookReplyForm!!.checkComment(max)){
            return "コメントの文字数が" + max.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_name_max)
        }
        return ""
    }
}