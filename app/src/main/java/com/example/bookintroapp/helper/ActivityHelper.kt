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
import com.example.bookintroapp.view.dialog.SimpleAlertDiralog
import com.example.bookintroapp.view.dialog.YesNoAlertDialog

class ActivityHelper {

    init{

    }

    // TODO static
    companion object{
        fun setLayout_gobackButton(activity : AppCompatActivity){
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        fun setLayout_gobackButton(view: View){
            //view.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        fun getStringDefine(activity: AppCompatActivity, id : Int) : String{
            return activity.resources.getString(id)
        }

        fun getStringDefine(flag: Fragment, id : Int) : String{
            return  flag.requireActivity().resources.getString(id)
        }

        fun getIntDefine(activity: AppCompatActivity, id : Int) : Int{
            return activity.resources.getString(id).toInt()
        }

        fun getIntDefine(flag: Fragment, id : Int) : Int{
            return flag.requireActivity().resources.getString(id).toInt()
        }

        fun show_error_dialog(activity : AppCompatActivity, contents: String){
            AlertDialog.Builder(activity)
                    .setTitle(activity.resources.getString(R.string.error_title))
                    .setMessage(contents)
                    .setPositiveButton("OK") { dialog, which ->  }
                    .show()
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

        fun show_success_dialog(activity: AppCompatActivity, title: Int, contents: Int, func: () -> Unit) {
            AlertDialog.Builder(activity)
                    .setTitle(activity.resources.getString(title))
                    .setMessage(activity.resources.getString(contents))
                    .setPositiveButton("OK", { dialog, which ->
                        func()
                    })
                    .show()
        }

        fun show_success_dialog(flag: Fragment, title: Int, contents: Int, func: () -> Unit){
            val titleString: String = flag.requireActivity().resources.getString(title)
            val contentsString: String = flag.requireActivity().resources.getString(contents)
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
            }.show(flag.parentFragmentManager, "success")
        }

        fun nextFragment(flag: Fragment, id: Int){
            // TODO フラグメントを遷移する
            flag.findNavController().navigate(id)
        }

        fun backFragment(flag: Fragment){
            // TODO フラグメント戻る
            flag.findNavController().popBackStack()
        }
    }
}