package com.example.observerwhite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EndGameDialog extends DialogFragment {

    public String dialogText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogText)
                .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("isLoose", true);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
        return builder.create();
    }
}