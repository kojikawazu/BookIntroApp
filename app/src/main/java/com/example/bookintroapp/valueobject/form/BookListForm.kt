package com.example.bookintroapp.valueobject.form

import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.valueobject.adapter.BookListAdapter
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity

// 書籍リストフォーム
class BookListForm() {

    constructor(bookListView: ListView) : this() {
        // TODO コンストラクタ
        BookListView = bookListView
    }

    // getter
    private var BookListView : ListView? = null
        get(){
            return field
        }

    fun createAdapter(frag: Fragment, list: MutableList<*>, user: UserEntity){
        // TODO 書籍リストアダプター生成
        val adapter = BookListAdapter(frag.requireContext(), R.layout.list_book_layout).apply {
            // リストデータ設定
            for(entity in list){
                add(entity as BookEntity?)
            }
            // サインインユーザデータ設定
            setUser(user)
        }
        BookListView?.adapter = adapter
    }

    fun setAdapter(adapter: BookListAdapter){
        // TODO アダプター設定
        BookListView?.adapter = adapter
    }

    fun setOnItemClick(frag: Fragment, fragmentId: Int){
        // TODO タップイベント設定
        BookListView?.setOnItemClickListener { parent, view, position, id ->
            // TODO 全体リスト画面をタップイベント

            // ターゲット書籍IDをアクティビティに保存
            saveBookId(frag, parent, position)

            // 書籍詳細画面へ遷移
            ActivityHelper.nextFragment(frag, fragmentId)
        }
    }

    private fun saveBookId(frag: Fragment, parent: AdapterView<*>, position: Int){
        // TODO アクティビティに書籍IDを保存
        val ac: MainActivity = frag.activity as MainActivity
        val selectView: ListView = parent as ListView
        val selectEntity: BookEntity = selectView.getItemAtPosition(position) as BookEntity

        ac.saveTargetBookId(selectEntity.BookId)
    }

}