package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.bb.vib.VibApp;
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.constant.Constant;
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces.IOnLoginListener;
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces.IOnResultListener;

import vn.mk.otp.OtpSdk;
import vn.mk.result.ResultCode;
import vn.mk.token.sdk.SdkNative;

public class SoftTokenSDKHelper {
    private static final String API_DO_ACTIVE_PREFIX = "3212";
    private static final String API_DO_ACTIVE = "https://103.10.212.123/keypass.ws/m/";


    private static SoftTokenSDKHelper sInstance;
//    private LoadingDialogWrapper mProgressDialog;

    private SoftTokenSDKHelper() {

    }

    public static SoftTokenSDKHelper getInstance(Context c) {
        OtpSdk.init(c);
        if (sInstance == null) {
            sInstance = new SoftTokenSDKHelper();
        }
        return sInstance.initDialog(c);
    }

    private SoftTokenSDKHelper initDialog(Context c) {
        if (c == null) {
            return sInstance;
        }
//        mProgressDialog = new LoadingDialogWrapper(c, new DialogInterface.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//            }
//        });
//        mProgressDialog.setCancelable(false);
        return sInstance;
    }

    public void showLoading() {
        try {
//            mProgressDialog.show();
            Log.e("Tag", "sdk dialog SHOW");
        } catch (WindowManager.BadTokenException e) {
            Log.e("Tag", "BadTokenException ");
        } catch (RuntimeException e) {
            Log.e("Tag", "RuntimeException ");
        } catch (Exception e) {
            Log.e("Tag", "Exception ");
        }
    }

    public void hideLoading() {
        try {
//            mProgressDialog.dismiss();
            Log.e("Tag", "sdk dialog DISMISS");
        } catch (WindowManager.BadTokenException e) {
            Log.e("Tag", "BadTokenException ");
        } catch (RuntimeException e) {
            Log.e("Tag", "RuntimeException ");
        } catch (Exception e) {
            Log.e("Tag", "Exception ");
        }
    }

    public void doActive(final String activeCode, final IOnResultListener iOnResultListener) {
        showLoading();
        Thread t = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                byte[] content =
                        SdkNative.getInstance().doActive(API_DO_ACTIVE_PREFIX + activeCode,
                                API_DO_ACTIVE);
                if (content == null) {
                    iOnResultListener.onFail("");
                    return;
                }
                String activeResult = new String(content);
                Log.e("Tag", "doActive response " + activeResult);

                if (ResultCode.SUCCESS.equals(activeResult)) { //success
                    iOnResultListener.onSuccess(activeResult);
                } else if (ResultCode.ACT_INCORRECT_ACTIVATION_CODE.equals(activeResult)) {
                    //incorrect activation code
                    iOnResultListener.onFail(activeResult);
                } else if (ResultCode.ACT_EXPIRED_ACTIVATION_CODE.equals(activeResult)) {
                    //expired activation code
                    iOnResultListener.onFail(activeResult);
                } else {
                    iOnResultListener.onFail(activeResult);
                }
                Looper.loop();
            }
        });

        t.start();
    }

    public void getOTPCR(String cr, final IOnResultListener iOnResultListener) {
        Log.e("Tag", "getOTPCR cr " + cr);
        byte[] content = SdkNative.getInstance().getCRotp(cr, "1");
        if (content == null) {
            iOnResultListener.onFail("");
            return;
        }
        String activeResult = new String(content);
        Log.e("Tag", "getOTPCR response " + activeResult);
        iOnResultListener.onSuccess(activeResult);
    }

    public boolean checkSoftTokenPermission() {
        return ContextCompat.checkSelfPermission(VibApp.Companion.applicationContext(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkSdkActive() {
        if (!checkSoftTokenPermission()) {
            return false;
        }
        try {
            return SdkNative.getInstance().checkAppActivated();
        } catch (Exception ex) {
            Log.e("Tag", ex.getMessage());
            return false;
        }
    }

    public int getTimeStep() {
        return SdkNative.getInstance().getTimeStep();
    }

    public void setTimeStep(int value) {
        if (checkSoftTokenPermission()) {
            Log.e("Tag", "setTimeStep call");
            SdkNative.getInstance().setTimeStep(value);
        } else {
            Log.e("Tag", "setTimeStep but no permission");
        }
    }

    public String getTokenSerial() {
        if (!checkSdkActive()) {
            return "";
        }
        byte[] content = SdkNative.getInstance().getTokenSn();
        if (content == null) {
            return "";
        }
        Log.e("Tag", "getTokenSerial " + new String(content));
        return new String(content);
    }

    /**
     * login by PIN and check lock PIN when login fail
     *
     * @param pin
     * @param iOnLoginListener
     */
    public void loginPIN(String pin, final IOnLoginListener iOnLoginListener) {
        int loginTimes = SharedPrefUtils.getSoftTokenLoginTime();
        // check lock
        if (loginTimes > Constant.TOTAL_LOGIN_TIMES) {
            iOnLoginListener.onLocked();
        } else {
            // login
            byte[] content = SdkNative.getInstance().loginPin(pin);
            String result;
            if (content != null && ResultCode.SUCCESS.equals(result = new String(content))) {
                //success
                // reset login failed times
                resetLoginFailTimes();
                iOnLoginListener.onSuccess();
            } else {
                // check alert login wrong many times
                if (loginTimes == Constant.TOTAL_LOGIN_TIMES - 1) {
                    iOnLoginListener.onWrongPINManyTimes();
                } else if (loginTimes == Constant.TOTAL_LOGIN_TIMES) {
                    iOnLoginListener.onLocked();
                } else {
                    iOnLoginListener.onWrongPIN(loginTimes);
                }
                // save login wrong times
                SharedPrefUtils.setSoftTokenLoginTime(++loginTimes);
            }
        }
    }

    /**
     * check locked PIN
     *
     * @return
     */
    public boolean isLockedPIN() {
        return SharedPrefUtils.getSoftTokenLoginTime() >= Constant.TOTAL_LOGIN_TIMES;
    }

    /**
     * Reset login fail times
     */
    public void resetLoginFailTimes() {
        // reset login failed times
        SharedPrefUtils.setSoftTokenLoginTime(Constant.FIRST_TIME_LOGIN);
    }

    public void setPin(String pin) {
        SdkNative.getInstance().setPin(pin);
    }

    public void changePin(String old, String newPin, final IOnResultListener iOnResultListener) {
        byte[] content = SdkNative.getInstance().changePin(old, newPin);
        if (content == null) {
            iOnResultListener.onFail("");
            return;
        }
        String result = new String(content);
        if (ResultCode.SUCCESS.equals(result)) { //success
            iOnResultListener.onSuccess(result);
        } else {
            iOnResultListener.onFail(result);
        }
    }

    /**
     * sync time sdk
     *
     * @param onResultListener
     */
    public void doSyncTime(final IOnResultListener onResultListener) {
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] result = SdkNative.getInstance().doSyncTime();
                String resultCode = "";
                hideLoading();
                if (result != null && ResultCode.SUCCESS.equals(resultCode = new String(result))) {
                    onResultListener.onSuccess(resultCode);
                } else {
                    onResultListener.onFail(resultCode);
                }
            }
        }).start();
    }
}
