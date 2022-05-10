package com.bb.vib.ui.accounts.forgotYourAccount.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.bb.vib.R

class AlertDialogPrompt(context: Context) : Dialog(context, R.style.Custom_Dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_alert_prompt_dialog)
    }
}