package com.bb.vib.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.bb.vib.R;

public class VibProgressDialog extends Dialog {
    public VibProgressDialog(@NonNull Context context) {
        super(context, R.style.Progress_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress_dialog_vib);
    }

    public void setTitle(String text) {

    }

    public void setMessage(String text) {

    }
}
