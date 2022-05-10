package com.bb.vib.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.bb.vib.R


class SmartOtpGenericTextWatcher(codeArray: Array<EditText>, view: View) : TextWatcher {

    val editText = codeArray
    val view = view

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()

        if (editText.size <= 4) {

            when (view.id) {
                R.id.editPin1 -> if (text.length == 1) editText[1].requestFocus()
                R.id.editPin2 -> if (text.length == 1) editText[2].requestFocus() else if (text.length == 0) editText[0].requestFocus()
                R.id.editPin3 -> if (text.length == 1) editText[3].requestFocus() else if (text.length == 0) editText[1].requestFocus()
                R.id.editPin4 -> if (text.length == 0) editText[3].requestFocus()
            }

        } else {

            when (view.id) {
                R.id.editPin1 -> if (text.length == 1) editText[1].requestFocus()
                R.id.editPin2 -> if (text.length == 1) editText[2].requestFocus() else if (text.length == 0) editText[0].requestFocus()
                R.id.editPin3 -> if (text.length == 1) editText[3].requestFocus() else if (text.length == 0) editText[1].requestFocus()
                R.id.editPin4 -> if (text.length == 1) editText[4].requestFocus() else if (text.length == 0) editText[2].requestFocus()
                R.id.editPin5 -> if (text.length == 1) editText[5].requestFocus() else if (text.length == 0) editText[3].requestFocus()
                R.id.editPin6 -> if (text.length == 0) editText[5].requestFocus()
            }

        }

    }
}