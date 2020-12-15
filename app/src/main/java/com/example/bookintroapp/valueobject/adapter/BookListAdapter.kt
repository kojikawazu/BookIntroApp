package com.example.bookintroapp.valueobject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.bookintroapp.R
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.BookRepository
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IMarkRepository
import com.example.bookintroapp.repository.MarkRepository
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.common.internal.FallbackServiceBroker
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// リスト項目を再利用するためのホルダー
data class ViewHolder(val booknameView: TextView, val titleView: TextView, val satis: TextView,
                      val commentView: TextView, val createdView: TextView, val niceView: TextView,
                      val niceButton: Button, val markView: TextView, val markButton: Button,
                        val replyButton: Button)

// ブックリスト用アダプター
class BookListAdapter : ArrayAdapter<BookEntity> {

    // リポジトリ
    private val _bookRepository: IBookRepository = BookRepository()
    private val _markRepository: IMarkRepository = MarkRepository()

    // レイアウト
    private var inflater: LayoutInflater

    // レイアウト番号
    private var resourceId : Int = 0

    // ユーザエンティティ
    private var user: UserEntity? = null

    init{
        // TODO 初期化
    }

    constructor(context: Context, id: Int) : super(context, id){
        // TODO コンストラクタ
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        resourceId = id
    }

    // TODO ユーザ設定
    fun setUser(inUser: UserEntity){ user = inUser }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO ビュー設定

        var holder : ViewHolder?
        var view = convertView
        if(view == null){
            // 初期時はここを通過↓↓↓

            // リストビュー生成
            view = inflater.inflate(resourceId, parent, false)

            // レイアウトIDとのバインド
            holder = ViewHolder(
                    view.findViewById(R.id.booklist_userName),
                    view.findViewById(R.id.booklist_title),
                    view.findViewById(R.id.booklist_satis),
                    view.findViewById(R.id.booklist_comment),
                    view.findViewById(R.id.booklist_created),
                    view.findViewById(R.id.booklist_niceCnt),
                    view.findViewById(R.id.button_niceCnt),
                    view.findViewById(R.id.booklist_markCnt),
                    view.findViewById(R.id.button_markCnt),
                    view.findViewById(R.id.button_reply)
            )
            view.tag = holder
        }
        else{
            // 2回目以降は保存されてるホルダー使用
            holder = view.getTag() as ViewHolder
        }

        // 番号別に設定
        val listItem = getItem(position)

        // リストデータバインド
        bindListData(holder, listItem)

        // クリックリスナー
        holder.niceButton.setOnClickListener{ _ ->
            // TODO いいね押下時
            OnNiceCntEventlistener(holder, listItem)
        }
        holder.markButton.setOnClickListener{ _ ->
            OnBookMarkEventListener(holder, listItem)
        }
        holder.replyButton.setOnClickListener{ _ ->
            Log.d("BookListAdapter", "reply $position")
        }
        return view!!
    }

    private fun bindListData(holder: ViewHolder, listItem: BookEntity?){
        // TODO リストデータバインド
        holder.booknameView.text = listItem?.BookName
        holder.titleView.text = listItem?.BookTitle
        holder.niceView.text = listItem?.NiceCntDisplay
        holder.commentView.text = listItem?.Comment
        holder.createdView.text = listItem?.Created.toString()

        // 更新が必要な部品の設定
        updateBookMarkUI(holder, listItem)
    }

    private fun updateBookMarkUI(holder: ViewHolder, listItem: BookEntity?){
        // TODO アクション後の更新処理

        // ブックマーク数を更新
        holder.markView.text = getBookMarkCount(listItem)

        // 自身の書籍の場合、ブックマークボタンを無効に
        holder.markButton.isEnabled = isBookMark_byUser(listItem)
    }

    private fun getBookMarkCount(listItem: BookEntity?): String{
        // TODO 各ブックマークの合計を取得
        val tsk: Task<QuerySnapshot> = _markRepository.select_byBookId(listItem!!.BookId)
        _markRepository.execing(tsk)
        if(tsk.isSuccessful){
            val count: Int = _markRepository.getResultEntiryCount(tsk)
            return count.toString()
        }
        else {
            return ""
        }
    }

    private fun isBookMark_byUser(listItem: BookEntity?): Boolean{
        // TODO 保留
        val tsk: Task<QuerySnapshot> = _markRepository.select_byuserId_bookId(user!!.UserId, listItem!!.BookId)
        _markRepository.execing(tsk)
        if(tsk.isSuccessful){
            val count: Int = _markRepository.getResultEntiryCount(tsk)
            return count == 0
        }else{
            return false
        }
    }

    private fun OnNiceCntEventlistener(holder: ViewHolder, listItem: BookEntity?){
        // TODO いいね押下時のイベント処理
        listItem?.plus_niceCnt()
        holder.niceView.text = listItem?.NiceCntDisplay

        // firebase更新
        val tsk: Task<Void> =  _bookRepository.update_niceCnt_byId(listItem?.BookId!!, listItem?.NiceCnt)
        while(!tsk.isComplete){}
    }

    private fun OnBookMarkEventListener(holder: ViewHolder, listItem: BookEntity?){
        // TODO ブックマークボタンのイベント処理

        // ブックマーク登録
        val entityNew = MarkEntity("0", user!!.UserId, listItem!!.BookId, Date())
        val tskAdd: Task<DocumentReference> = _markRepository.insert(entityNew)
        _markRepository.execing(tskAdd)
        if( !tskAdd.isSuccessful ){
            // 追加に失敗
            return
        }

        // ブックマーク追加に成功
        updateBookMarkUI(holder, listItem)
    }




}