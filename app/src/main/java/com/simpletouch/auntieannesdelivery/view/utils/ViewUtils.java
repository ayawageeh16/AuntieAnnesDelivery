package com.simpletouch.auntieannesdelivery.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.simpletouch.auntieannesdelivery.R;

public class ViewUtils {

    public static void showToast(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    public static void showProgressBar(AppCompatActivity activity, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void hideProgressBar(AppCompatActivity activity, ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void disableUI(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableUI(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void showAlertDialogWithSingleAction(Activity activity, DialogInterface dialogInterface, String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity,
                R.style.BaseDialogTheme);

        LayoutInflater inflater = activity.getLayoutInflater();
        View customTitleView = inflater.inflate(R.layout.dialog_title, null);
        TextView tvTitle = customTitleView.findViewById(R.id.dialogTitleTxt);
        tvTitle.setText(title);
        tvTitle.setCompoundDrawables(null, null, null, null);

        builder.setCustomTitle(customTitleView)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialogInterface.cancel());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showAlertDialog(AppCompatActivity activity, DialogInterface dialogEventListener, String title, String message) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity,
                R.style.BaseDialogTheme);

        LayoutInflater inflater = activity.getLayoutInflater();
        View customTitleView = inflater.inflate(R.layout.dialog_title, null);
        TextView tvTitle = customTitleView.findViewById(R.id.dialogTitleTxt);
        tvTitle.setText(title);
        tvTitle.setCompoundDrawables(null, null, null, null);

        builder.setCustomTitle(customTitleView)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialogEventListener.cancel())
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialogEventListener.dismiss());

        android.app.AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

}
