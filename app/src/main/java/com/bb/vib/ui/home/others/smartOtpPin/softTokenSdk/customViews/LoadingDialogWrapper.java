package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.customViews;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.WindowManager.BadTokenException;

import java.lang.ref.SoftReference;

public class LoadingDialogWrapper {

    private SoftReference<Context> mContextWrapper;
    private LoadingDialog mLoadingDialog;

    public LoadingDialogWrapper(Context context) {
        mContextWrapper = new SoftReference<>(context);
//        mLoadingDialog = LoadingDialog.newInstance(mContextWrapper.get());
        mLoadingDialog.setCancelable(false);
    }

    public LoadingDialogWrapper(Context context, OnCancelListener listen) {
        mContextWrapper = new SoftReference<>(context);
//        mLoadingDialog = LoadingDialog.newInstance(mContextWrapper.get());
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setOnCancelListener(listen);
    }

    /**
     * set cancelable
     * @param value
     */
    public void setCancelable(boolean value) {
        mLoadingDialog.setCancelable(value);
    }

    /**
     * set message
     * @param msg
     */
    public void setMessage(String msg) {
        mLoadingDialog.setMessage(msg);
    }

    /**
     * set message
     * @param msgId
     */
    public void setMessage(int msgId) {
        mLoadingDialog.setMessage(msgId);
    }

    /**
     * check dialog showing
     * @return
     */
    public boolean isShowing() {
        return mLoadingDialog.isShowing();
    }

    /**
     * show dialog
     */
    public void show() {
        try {
            mLoadingDialog.show();
        } catch (BadTokenException e) {
            Log.e("Tag", "BadTokenException in show()");
        } catch (Exception e) {
            Log.e("Tag", "Exception in show()");
        }
    }

    /**
     * dismiss dialog
     */
    public void dismiss() {
        try {
            mLoadingDialog.dismiss();
        } catch (BadTokenException e) {
            Log.e("Tag", "BadTokenException in show()");
        }
    }

    /**
     * on dispose
     */
    public void onDispose() {
        mContextWrapper.clear();
    }

}
