package com.ajm.employee.utils

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ajm.employee.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Anish on 8/25/2020.
 */
 class SnackBarUtils: AppCompatActivity() {

    fun showSnackBar(parentView: View, message: String?) {
        val snackbar = Snackbar.make(parentView, message!!, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(parentView.context,R.color.colorPrimary))
        val textView =
            sbView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setPadding(8, 8, 8, 8)
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 3
        snackbar.show()
    }
}