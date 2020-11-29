package com.example.bookintroapp.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class YesNoAlertDialog : DialogFragment() {
    var onPositiveListener: DialogInterface.OnClickListener? = null
    var onNegativeListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(arguments?.getString("title"))
            setMessage(arguments?.getString("message"))
            setPositiveButton(arguments?.getString("positiveButtonLabel"), onPositiveListener)
            setNegativeButton(arguments?.getString("negativeButtonLabel"),onNegativeListener)
        }
        return builder.create()
    }
}