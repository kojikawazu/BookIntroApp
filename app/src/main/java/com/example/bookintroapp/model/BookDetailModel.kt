package com.example.bookintroapp.model

import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.form.BookDetailForm

// 書籍詳細モデル
class BookDetailModel : ModelBase() {

    // エンティティ
    private var bookEntity: BookEntity? = null

    // フォーム
    private var bookDetailForm: BookDetailForm? = null

    // リポジトリ
    private var _bookRepository: IBookRepository = BookRepository()

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
            view.findViewById(R.id.bookview_iine_textView)
        )
    }

    override fun setListener(view: View, frag: Fragment) {
        // TODO イベントリスナーの設定
        val ac: MainActivity = frag.activity as MainActivity
        val targetBookId = ac.getTargetBookId()

        // 書籍エンティティ取得
        bookEntity = ActivityHelper.selectBookEntity(targetBookId, _bookRepository)

        // データ反映
        bookDetailForm?.setData(bookEntity!!.BookTitle, bookEntity!!.BookTitle, bookEntity!!.Comment,
                                bookEntity!!.SatisCntDisplay, bookEntity!!.NiceCntDisplay)


    }
}