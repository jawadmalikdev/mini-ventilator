package com.example.miniventilator;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingbar {
    Activity activity;
    AlertDialog dialog;
    public Loadingbar(Activity thisactivity){
        activity = thisactivity;
    }
    public void showdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_layout, null));
        dialog = builder.create();
        dialog.show();
    }
    public void dismissbar(){
        dialog.dismiss();
    }
}

