package com.example.bookintroapp.valueobject.form

import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.INiceReplyRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.repository.NiceReplyRepository
import com.example.bookintroapp.repository.UserRepository
import com.example.bookintroapp.valueobject.entity.NiceReplyEntity
import com.example.bookintroapp.valueobject.entity.ReplyEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import java.util.*

// リプライリストフォーム
class ReplyListForm() {

    // リプライ追加するレイアウト群
    val layout: LinearLayout? = null

    // エンティティ
    private var userEntity: UserEntity? = null

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _niceReplyRepository: INiceReplyRepository = NiceReplyRepository()

    constructor(layout: LinearLayout) : this() {
        // TODO コンストラクタ
        ReplyParentLayout = layout
    }

    // getter
    private var ReplyParentLayout: LinearLayout? = null
        get(){
            return field
        }

    fun createReplyLayout(frag: Fragment,list: MutableList<ReplyEntity>){
        // TODO 返信リストの追加生成
        val ac: MainActivity = frag.activity as MainActivity

        // ユーザエンティティ取得
        userEntity = FirebaseHelpler.selectUserEntity(frag, _userRepository)

        for(entity in list){
            // 子レイアウト生成
            val layout: LinearLayout = ac.layoutInflater.inflate(R.layout.list_reply_layout, null) as LinearLayout

            // 部品検索
            val userView: TextView = layout.findViewById(R.id.reply_user)
            val satisView: TextView = layout.findViewById(R.id.reply_satis)
            val commentView: TextView = layout.findViewById(R.id.reply_comment)
            val createdView: TextView = layout.findViewById(R.id.reply_created)
            val niceCntView: TextView = layout.findViewById(R.id.reply_niceCnt)
            val niceCntButton: Button = layout.findViewById(R.id.reply_niceButton)

            // 値を反映
            satisView.text = entity.SatisDisplay
            commentView.text = entity.Comment
            createdView.text = entity.Created.toString()
            niceCntView.text = entity.NiceCntDisplay
            // ユーザ名の反映
            setUserName(userView, entity)

            // イベントリスナー設定
            niceCntButton.setOnClickListener{ _ ->
                OnClickListener(niceCntView, entity)
            }

            // 親レイアウトに追加
            ReplyParentLayout?.addView(layout)
        }
    }

    private fun setUserName(userView: TextView, entity: ReplyEntity){
        // TODO ユーザIDによりユーザ名の反映
        val tsk: Task<DocumentSnapshot> =  _userRepository.select_byId(entity.UserId)
        _userRepository.execing(tsk)
        if(_userRepository.isSuccessed(tsk)){
            val user: UserEntity? = _userRepository.getResultEntityD(tsk)
           if(user != null){
               userView.text = user.UserName
           }
        }
    }

    private fun OnClickListener(niceCntView: TextView, replyEntity: ReplyEntity){
        // TODO いいねボタン押下処理

        val entityNew = NiceReplyEntity(replyEntity, userEntity!!, Date())
        val tskadd: Task<DocumentReference> =  _niceReplyRepository.insert(entityNew)
        _niceReplyRepository.execing(tskadd)

        if( _niceReplyRepository.isSuccessed(tskadd)){
            // 追加成功してたら、合計取得
            val tsk: Task<QuerySnapshot> = _niceReplyRepository.select_byReplyId(replyEntity.ReplyId)
            _niceReplyRepository.execing(tsk)
            val cnt = _niceReplyRepository.getResultEntiryCount(tsk)

            // 値を反映
            replyEntity.setNiceCnt(cnt)
            niceCntView.text = replyEntity.NiceCntDisplay
        }
    }

}