package com.example.bookintroapp.model

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.valueobject.form.BookAddForm
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import java.util.*

// 書籍追加モデル
class BookAddModel : ModelBase() {

    // ユーザーエンティティ
    private var userEntity: UserEntity? = null

    // 書籍追加フォーム
    private var bookAddForm: BookAddForm? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _bookRepository: IBookRepository = BookRepository()

    init{
        // TODO 初期化
    }

    override fun setLayout(view: View) {
        // TODO ビューの設定
        bookAddForm = BookAddForm(
                view.findViewById(R.id.bookadd_book_edit),
                view.findViewById(R.id.bookadd_booktitle_edit),
                view.findViewById(R.id.bookadd_bookcontents_edit),
                view.findViewById(R.id.bookadd_satis_spin),
                view.findViewById(R.id.bookadd_button)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナー追加

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag,_userRepository)

        bookAddForm?.setOnClickListener {
            // TODO 書籍追加タップ
            onClickListener_bookadd(frag)
        }
    }

    fun onClickListener_bookadd(frag: Fragment){
        // TODO 書籍追加イベント

        // バリデーションチェック
        // ----------------------------------------------------------------------------------------
        val isError : Boolean = ActivityHelper.checkValidate(frag) { isValidate(frag) }
        // エラーチェック
        if( !isError ){
            return
        }

        // Firebaseへデータ登録処理
        // ----------------------------------------------------------------------------------------
        val entityNew = BookEntity(userEntity!!, bookAddForm!!, Date())
        val tskAdd: Task<DocumentReference> = _bookRepository.insert(entityNew)
        _bookRepository.execing(tskAdd)
        if( !_bookRepository.isSuccessed(tskAdd) ){
            // 追加に失敗
            // エラーダイアログ表示
            ActivityHelper.show_error_dialog(frag, R.string.book_add_error_dialog)
            return
        }

        // 追加登録完了
        // ----------------------------------------------------------------------------------------
        // サインアップ成功ダイアログ
        ActivityHelper.show_success_dialog(frag,
                R.string.book_add_success_title, R.string.book_add_success_contents) {
            // OKの場合、サインアップ画面閉じる
            ActivityHelper.backFragment(frag)
        }
    }

    fun isValidate(flag: Fragment) : String{
        // TODO バリデーションチェック
        // 空チェック
        if( bookAddForm!!.isEmpty() ){
            return ActivityHelper.getStringDefine(flag, R.string.error_form_empty)
        }

        // 書籍名チェック
        var max : Int = ActivityHelper.getIntDefine(flag, R.string.bookadd_bookname_len)
        if( !bookAddForm!!.checkBookName(max)){
            return "書籍名の文字数が" + max.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_name_max)
        }

        // タイトルチェック
        max = ActivityHelper.getIntDefine(flag, R.string.bookadd_title_len)
        if( !bookAddForm!!.checkTitle(max) ){
            return "タイトルの文字数が" + max.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_name_max)
        }

        // 書籍名チェック
        max =  ActivityHelper.getIntDefine(flag, R.string.bookadd_comment_len)
        if( !bookAddForm!!.checkComment(max) ){
            return "書籍名の文字数が" + max.toString() + ActivityHelper.getStringDefine(flag, R.string.error_form_name_max)
        }

        // 成功時は空白返す
        return ""
    }

}