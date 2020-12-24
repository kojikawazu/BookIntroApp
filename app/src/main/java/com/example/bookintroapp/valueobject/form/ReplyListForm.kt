package com.example.bookintroapp.valueobject.form

import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.helper.ActivityHelper
import com.example.bookintroapp.helper.FirebaseHelpler
import com.example.bookintroapp.repository.*
import com.example.bookintroapp.valueobject.button.NiceCntReplyButton
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

    // エンティティ
    private var userEntity: UserEntity? = null

    // レイアウト
    private val niceReplyButton: NiceCntReplyButton = NiceCntReplyButton()

    // リポジトリ
    private val _userRepository: IUserRepository = UserRepository()
    private val _replyRepository: IReplyRepository = ReplyRepository()

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
            val satisImage: ImageView = layout.findViewById(R.id.reply_viewimage)
            val commentView: TextView = layout.findViewById(R.id.reply_comment)
            val createdView: TextView = layout.findViewById(R.id.reply_created)
            val niceCntView: TextView = layout.findViewById(R.id.reply_niceCnt)
            val niceCntButton: Button = layout.findViewById(R.id.reply_niceButton)

            // 値を反映
            satisView.text = entity.SatisDisplay
            commentView.text = entity.Comment
            createdView.text = entity.Created.toString()
            niceCntView.text = entity.NiceCntDisplay

            // 満足度イメージの設定
            ActivityHelper.setImage_satisfaction(satisView, satisImage, entity.Satis)
            
            // ユーザ名の反映
            setUserName(userView, entity)

            // いいねボタンの更新
            updateNiceButtonUI(niceCntButton, entity)

            // イベントリスナー設定
            niceCntButton.setOnClickListener{ _ ->
                // TODO いいねボタン押下時
                OnClickListener(niceCntView, niceCntButton, entity)
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

    private fun updateNiceButtonUI(niceCntButton: Button, entity: ReplyEntity){
        // TODO ボタンの非活性制御
        if(userEntity == null)  return
        niceCntButton.isEnabled = niceReplyButton.isNiceCnt_byUser(userEntity!!, entity)
    }

    private fun OnClickListener(niceCntView: TextView, niceCntButton: Button, replyEntity: ReplyEntity){
        // TODO いいねボタン押下処理
        if(userEntity == null)  return

        // いいねデータ追加
        val ret = niceReplyButton.OnNiceCntEventlistener(userEntity!!, replyEntity)
        if(ret){
            // 追加成功

            // いいね数を更新
            replyEntity.setNiceCnt(niceReplyButton.getNiceCntCount(replyEntity).toInt())
            // ビューに反映
            niceCntView.text = replyEntity.NiceCntDisplay

            // 書籍テーブルのいいねカウンタの更新
            val tsk: Task<Void> = _replyRepository.update_niceCnt_byId(replyEntity.ReplyId, replyEntity.NiceCnt)
            _replyRepository.execing(tsk)

            // UI更新
            updateNiceButtonUI(niceCntButton, replyEntity)
        }
    }

}