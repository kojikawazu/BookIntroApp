package com.example.bookintroapp.helper

import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings.System.putString
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.view.dialog.SimpleAlertDiralog
import com.example.bookintroapp.view.dialog.YesNoAlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class ActivityHelper {

    init{

    }

    // TODO static
    companion object{
        fun getStringDefine(frag: Fragment, id : Int) : String{
            return  frag.requireActivity().resources.getString(id)
        }

        fun getIntDefine(frag: Fragment, id : Int) : Int{
            val ac: MainActivity = frag.activity as MainActivity
            return ac.resources.getString(id).toInt()
        }

        fun show_error_dialog(flag: Fragment, contents: String){
            val title: String = flag.requireActivity().resources.getString(R.string.error_title)
            val yesString: String = flag.requireActivity().resources.getString(R.string.dialog_yes)
            SimpleAlertDiralog().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("message", contents)
                    putString("positiveButtonLabel", yesString)
                }
                onPositiveListener = DialogInterface.OnClickListener { dialog, which ->
                    //OKボタンリスナー
                }
            }.show(flag.parentFragmentManager, "error")
        }

        fun show_success_dialog(frag: Fragment, title: Int, contents: Int, func: () -> Unit){
            val ac: MainActivity = frag.activity as MainActivity
            val titleString: String = ac.resources.getString(title)
            val contentsString: String = ac.resources.getString(contents)
            SimpleAlertDiralog().apply {
                arguments = Bundle().apply {
                    putString("title", titleString)
                    putString("message", contentsString)
                    putString("positiveButtonLabel", "OK")
                }
                onPositiveListener = DialogInterface.OnClickListener { dialog, which ->
                    //OKボタンリスナー
                    func()
                }
            }.show(frag.parentFragmentManager, "success")
        }

        fun nextFragment(flag: Fragment, id: Int){
            // TODO フラグメントを遷移する
            flag.findNavController().navigate(id)
        }

        fun backFragment(flag: Fragment){
            // TODO フラグメント戻る
            flag.findNavController().popBackStack()
        }

        fun selectUserEntity(frag: Fragment, _userRepository: IUserRepository): UserEntity?{
            try {
                // TODO アクティビティに保存してるメールアドレスからユーザデータ取得
                val ac: MainActivity = frag.activity as MainActivity
                val emailString = ac.getSigninMail()

                // ユーザーエンティティ
                var tsk: Task<QuerySnapshot> = _userRepository.select_byEmail(emailString)
                while (!tsk.isComplete) {
                }
                return _userRepository.getResultEntity(tsk)
            }catch(ex: ClassCastException){
                return null
            }
        }
    }
}