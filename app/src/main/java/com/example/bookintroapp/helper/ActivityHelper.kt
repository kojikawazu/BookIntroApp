package com.example.bookintroapp.helper

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bookintroapp.R

class ActivityHelper {

    init{

    }

    // TODO static
    companion object{
        fun setLayout_gobackButton(activity : AppCompatActivity){
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        fun getStringDefine(activity: AppCompatActivity, id : Int) : String{
            return activity.resources.getString(id)
        }

        fun getIntDefine(activity: AppCompatActivity, id : Int) : Int{
            return activity.resources.getString(id).toInt()
        }

        fun show_error_dialog(activity : AppCompatActivity, contents: String){
            AlertDialog.Builder(activity)
                    .setTitle(activity.resources.getString(R.string.error_title))
                    .setMessage(contents)
                    .setPositiveButton("OK") { dialog, which ->  }
                    .show()
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
    }
}