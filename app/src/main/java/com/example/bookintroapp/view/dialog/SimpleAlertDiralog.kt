package com.example.bookintroapp.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bookintroapp.R

class SimpleAlertDiralog() : DialogFragment() {

    var onPositiveListener:DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(arguments?.getString("title"))
            setMessage(arguments?.getString("message"))
            setPositiveButton(arguments?.getString("positiveButtonLabel"), onPositiveListener)
        }
        return builder.create()
    }
}