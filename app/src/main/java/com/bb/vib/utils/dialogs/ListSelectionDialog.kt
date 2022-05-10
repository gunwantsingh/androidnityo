package com.bb.vib.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.bb.vib.R

class ListSelectionDialog(context: Context) : Dialog(context, R.style.Custom_Dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_list_selection_dialog)
    }
}