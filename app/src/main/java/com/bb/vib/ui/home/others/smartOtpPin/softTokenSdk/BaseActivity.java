
package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {

    public static final int FLAG_RE_ACTIVE_FOR_AUTH_PIN = 2;
    public static final int FLAG_RE_CREATE_PIN = 1;
    public static final int FLAG_ACTIVE = 0;
    public static final String EXTRA_FLAG_FOR_RE_CREATE_PIN = "FLAG_FOR_CREATE_PIN";

    /*
     * View
     */
    protected ImageView mIvBackground;
    /*
     * Variable
     */
//    private LoadingDialogWrapper mLoadingDialogWrapper;
    private List<Dialog> mDialogList;

    /**
     * check for case re-create PIN
     * @return
     */
    protected boolean isForReCreatePin() {
        return getIntent() != null
                && getIntent().getIntExtra(EXTRA_FLAG_FOR_RE_CREATE_PIN, FLAG_ACTIVE) == FLAG_RE_CREATE_PIN;
    }

    /**
     * add dialog for dismiss when finish activity
     *
     * @param dialog
     */
    public void addDialog(Dialog dialog) {
        if (mDialogList != null) {
            mDialogList.add(dialog);
        }
    }

    /**
     * remove dialog when dialog dismiss
     *
     * @param dialog
     */
    public void removeDialog(Dialog dialog) {
        if (mDialogList != null && !mDialogList.isEmpty()) {
            mDialogList.remove(dialog);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mDialogList = new ArrayList<>();
//        mLoadingDialogWrapper = new LoadingDialogWrapper(this);
    }

    /**
     * @return
     */
    public abstract int getContentViewId();

    public void setProgressText(String text) {
//        if (mLoadingDialogWrapper != null) {
//            mLoadingDialogWrapper.setMessage(text);
//        }
    }

    public void showLoading() {
//        if (mLoadingDialogWrapper != null) {
//            mLoadingDialogWrapper.show();
//        }
    }

    public void hideLoading() {
//        if (mLoadingDialogWrapper != null) {
//            mLoadingDialogWrapper.dismiss();
//        }
    }

    @Override
    public void onDestroy() {
        if (mDialogList != null && !mDialogList.isEmpty()) {
            // dismiss all dialog avoid exception
            Dialog dialog;
            for (int i = 0; i < mDialogList.size(); i++) {
                dialog = mDialogList.get(i);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            mDialogList.clear();
            mDialogList = null;
        }

        super.onDestroy();
    }

    protected void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
