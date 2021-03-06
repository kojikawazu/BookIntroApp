package com.example.bookintroapp.helper

import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings.System.putString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.bookintroapp.R
import com.example.bookintroapp.activity.MainActivity
import com.example.bookintroapp.repository.IBookRepository
import com.example.bookintroapp.repository.IUserRepository
import com.example.bookintroapp.valueobject.adapter.BookListAdapter
import com.example.bookintroapp.valueobject.entity.BookEntity
import com.example.bookintroapp.valueobject.entity.UserEntity
import com.example.bookintroapp.view.dialog.SimpleAlertDiralog
import com.example.bookintroapp.view.dialog.YesNoAlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
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
            return frag.requireActivity().resources.getString(id).toInt()
        }

        fun show_error_dialog(frag: Fragment, contents: String){
            // TODO エラーダイアログ表示
            val title: String = getStringDefine(frag, R.string.error_title)
            val yesString: String = getStringDefine(frag, R.string.dialog_yes)
            SimpleAlertDiralog().apply {
                arguments = Bundle().apply {
                    // 部品反映
                    putString("title", title)
                    putString("message", contents)
                    putString("positiveButtonLabel", yesString)
                }
                onPositiveListener = DialogInterface.OnClickListener { dialog, which ->
                    // OKボタンリスナー
                }
            }.show(frag.parentFragmentManager, "error")
        }

        fun show_error_dialog(frag: Fragment, id: Int){
            // TODO エラーダイアログ表示
            val title: String = getStringDefine(frag, R.string.error_title)
            val yesString: String = getStringDefine(frag, R.string.dialog_yes)
            SimpleAlertDiralog().apply {
                arguments = Bundle().apply {
                    // 部品反映
                    putString("title", title)
                    putString("message", getStringDefine(frag, id))
                    putString("positiveButtonLabel", yesString)
                }
                onPositiveListener = DialogInterface.OnClickListener { dialog, which ->
                    //OKボタンリスナー
                }
            }.show(frag.parentFragmentManager, "error")
        }

        fun show_success_dialog(frag: Fragment, title: Int, contents: Int, func: () -> Unit){
            // TODO 成功ダイアログ表示
            val titleString: String = getStringDefine(frag, title)
            val contentsString: String = getStringDefine(frag, contents)
            SimpleAlertDiralog().apply {
                arguments = Bundle().apply {
                    // 部品反映
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

        fun checkValidate(frag: Fragment, checkFunc: (frag: Fragment) -> String) : Boolean{
            // TODO バリデーションチェック
            var errorString = checkFunc(frag)
            // エラーチェック
            if( !errorString.isEmpty() ){
                // エラーダイアログ表示
                show_error_dialog(frag, errorString)
                return false
            }
            return true
        }

        fun setImage_satisfaction(satisText: TextView, satisImage: ImageView, satis: Int){
            // TODO 満足度イメージの設定
            satisText.visibility = View.GONE
            when(satis){
                1 -> {  satisImage.setImageResource(R.drawable.star_1) }
                2 -> {  satisImage.setImageResource(R.drawable.star_2) }
                3 -> {  satisImage.setImageResource(R.drawable.star_3) }
                4 -> {  satisImage.setImageResource(R.drawable.star_4) }
                5 -> {  satisImage.setImageResource(R.drawable.star_5) }
                else ->{
                    satisImage.setImageResource(R.drawable.star_5)
                }
            }
        }

    }
}