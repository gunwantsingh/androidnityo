package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces;

public interface IOnLoginListener {
    void onWrongPIN(int wrongTimes);

    void onWrongPINManyTimes();

    void onLocked();

    void onSuccess();
}
