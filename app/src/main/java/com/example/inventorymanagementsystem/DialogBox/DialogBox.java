package com.example.inventorymanagementsystem.DialogBox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogBox {

    public DialogBox(Context context, String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
