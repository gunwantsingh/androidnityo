package com.bb.vib.ui.home.others

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.bb.vib.R

class BiometricDialog(context: Context) : Dialog(context, R.style.Custom_Dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.biometric_dialog)
    }
}