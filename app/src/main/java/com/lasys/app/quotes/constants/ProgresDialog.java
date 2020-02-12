package com.lasys.app.quotes.constants;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgresDialog {
    private ProgressDialog dialog;

    public ProgresDialog(Context context) {
        dialog = new ProgressDialog(context);
    }

    public void progresDialogShow(String message) {
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void progresDialogDissmiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
