
package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.customViews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bb.vib.R;
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.BaseActivity;

public class LoadingDialog extends Dialog {
    private static final int THEME_DEFAULT = -1;
    private TextView mTvMessage;
    private BaseActivity mOwnerActivity;

    public LoadingDialog(Context context) {
        super(context);
        initDialog(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialog(context);
    }

    /**
     * getInstance new instance dialog
     *
     * @param context
     * @return
     */
    public static LoadingDialog newInstance(Context context) {
        return new LoadingDialog(context);
    }

    private void initDialog(Context context) {
        if (context instanceof BaseActivity) {
            // add dialog for dismiss when finish activity
            mOwnerActivity = (BaseActivity) context;
            mOwnerActivity.addDialog(this);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_loading);
        mTvMessage = findViewById(R.id.tv_msg);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        // NOTE: 11/06/2019 sau 60s cho phep cancel
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setCanceledOnTouchOutside(true);
                setCancelable(true);
            }
        }, 60 * 1000);
    }

    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mTvMessage.setText(message);
        } else {
            mTvMessage.setVisibility(View.GONE);
        }

    }

    public void setMessage(int msgId) {
        mTvMessage.setText(getContext().getString(msgId));
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (BadTokenException e) {
            Log.e("Tag", "BadTokenException in show()");
        } catch (Exception e) {
            Log.e("Tag", "Exception in show()");
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            if (mOwnerActivity != null) {
                // remove dialog
                mOwnerActivity.removeDialog(this);
            }
        } catch (BadTokenException e) {
            Log.e("Tag", "BadTokenException in dismiss()");
        }
    }

    @Override
    public void cancel() {
        if (mOwnerActivity != null) {
            // remove dialog
            mOwnerActivity.removeDialog(this);
        }
        super.cancel();
    }
}
