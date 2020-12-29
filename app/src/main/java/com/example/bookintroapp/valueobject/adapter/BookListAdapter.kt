package com.example.bookintroapp.valueobject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.button.BookmarkButton
import com.example.bookintroapp.valueobject.button.NiceCntButton
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.MarkEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.common.internal.FallbackServiceBroker
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

// リスト項目を再利用するためのホルダー
data class ViewHolder(val booknameView: TextView, val titleView: TextView, val satis: TextView, val satisImage: ImageView,
                      val commentView: TextView, val createdView: TextView, val niceView: TextView,
                      val niceButton: Button, val markView: TextView, val markButton: Button,
                        val replyView: TextView, val replyButton: Button)

// ブックリスト用アダプター
class BookListAdapter : ArrayAdapter<BookEntity> {

    // レイアウト
    private var inflater: LayoutInflater
    private var niceCntButton: NiceCntButton = NiceCntButton()
    private var bookmarkButton: BookmarkButton = BookmarkButton()

    // レイアウト番号
    private var resourceId : Int = 0

    // ユーザエンティティ
    private var user: UserEntity? = null

    // フラグメント
    private var frag: Fragment? = null

    // リポジトリ
    private var _bookRepository: IBookRepository = BookRepository()

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

    // TODO フラグメント設定
    fun setFragment(inFrag: Fragment){  frag = inFrag   }

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
                    view.findViewById(R.id.booklist_imageview),
                    view.findViewById(R.id.booklist_comment),
                    view.findViewById(R.id.booklist_created),
                    view.findViewById(R.id.booklist_niceCnt),
                    view.findViewById(R.id.button_niceCnt),
                    view.findViewById(R.id.booklist_markCnt),
                    view.findViewById(R.id.button_markCnt),
                    view.findViewById(R.id.booklist_replyCnt),
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
            // TODO ブックマークボタン押下時
            OnBookMarkEventListener(holder, listItem)
        }
        holder.replyButton.setOnClickListener{ _ ->
            // TODO リプライボタン押下時
            OnReplyEventListener(listItem)
        }
        return view!!
    }

    private fun bindListData(holder: ViewHolder, listItem: BookEntity?){
        // TODO リストデータバインド
        holder.booknameView.text = listItem?.BookName
        holder.titleView.text = listItem?.BookTitle
        holder.satis.text = listItem?.SatisCntDisplay
        holder.niceView.text = listItem?.NiceCntDisplay
        holder.markView.text = listItem?.MarkCntDisplay
        holder.replyView.text = listItem?.ReplyCntDisplay
        holder.commentView.text = listItem?.Comment
        holder.createdView.text = listItem?.Created.toString()

        // 満足度イメージの設定
        ActivityHelper.setImage_satisfaction(holder.satis, holder.satisImage, listItem!!.SatisCnt)

        // 更新が必要な部品の設定
        // 処理が重いので修正必要
        updateBookMarkUI(holder, listItem)
    }

    private fun updateBookMarkUI(holder: ViewHolder, listItem: BookEntity?){
        // TODO アクション後の更新処理
        if(user == null || listItem == null)    return

        // 自身のユーザがブックマーク登録したかチェック
        holder.niceButton.isEnabled = niceCntButton.isNiceCnt_byUser(user!!, listItem)

        // 自身のユーザがブックマーク登録したかチェック
        holder.markButton.isEnabled = bookmarkButton.isBookMark_byUser(user!!, listItem)
    }

    private fun OnNiceCntEventlistener(holder: ViewHolder, listItem: BookEntity?){
        // TODO いいね押下時イベント
        if(user == null || listItem == null)    return
        val ret = niceCntButton.InsertNiceCnt(user!!, listItem)
        if(ret){
            // いいねリスト追加に成功

            // いいね数を更新
            listItem.setNiceCnt(niceCntButton.getNiceCntCount(listItem).toInt())
            // ビューに反映
            holder.niceView.text = listItem.NiceCntDisplay
            // 書籍テーブルのいいねカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_niceCnt_byId(listItem.BookId, listItem.NiceCnt)
            _bookRepository.execing(tsk)

            // UI更新
            holder.niceButton.isEnabled = niceCntButton.isNiceCnt_byUser(user!!, listItem)
        }
    }

    private fun OnBookMarkEventListener(holder: ViewHolder, listItem: BookEntity?){
        // TODO ブックマーク押下イベント

        // ブックマークリストに追加
        val ret = bookmarkButton.InsertBookMark(user!!, listItem!!)
        if(ret) {
            // ブックマーク追加に成功

            // ブックマークの合計を取得
            listItem.setMarkCnt(bookmarkButton.getBookMarkCount(listItem).toInt())
            // ビューに反映
            holder.markView.text = listItem.MarkCntDisplay
            // 書籍テーブルのブックマークカウンタの更新
            val tsk: Task<Void> = _bookRepository.update_markCnt_byId(listItem.BookId, listItem.MarkCnt)
            _bookRepository.execing(tsk)

            // UI更新
            holder.markButton.isEnabled = bookmarkButton.isBookMark_byUser(user!!, listItem)
        }
    }

    private fun OnReplyEventListener(listItem: BookEntity?){
        // TODO リプライボタン押下時処理

        // 書籍リプライ画面へ遷移
        if( frag != null && listItem != null){
            // 書籍IDをアクティビティに保存
            val ac: MainActivity = frag?.activity as MainActivity
            ac.saveTargetBookId(listItem!!.BookId)

            // 書籍詳細へ遷移
            ActivityHelper.nextFragment(frag!!, R.id.action_bookmain_to_bookreply)
        }
    }

}