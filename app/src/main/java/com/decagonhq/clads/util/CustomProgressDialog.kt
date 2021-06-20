package com.decagonhq.clads.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.decagonhq.clads.R

class CustomProgressDialog(context: Context) : AlertDialog(context) {
    private val messageTextView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null)
        messageTextView = view.findViewById(R.id.message)
        setView(view)
    }

    override fun setMessage(message: CharSequence?) {
        this.messageTextView.text = message.toString()
    }

    fun showDialogFragment(message: String) {
        this.setMessage(message)
        this.setCanceledOnTouchOutside(false)
        this.setCancelable(false)
        this.show()
    }

    fun hideProgressDialog() {
        this.dismiss()
    }
}
