package com.example.observerwhite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.observerwhite.databinding.FragmentEndGameDialogBinding;
import com.example.observerwhite.databinding.FragmentNewGameInfoBinding;

public class EndGameDialog extends DialogFragment {

    public String dialogText;
    public int imgResource;
    public FragmentEndGameDialogBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentEndGameDialogBinding.inflate(inflater);
        binding.eventImage.setImageResource(imgResource);

        builder.setView(binding.getRoot());

        builder.setCancelable(true);

        binding.text.setText(dialogText);

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("isLoose", true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return builder.create();

//        builder.setMessage(dialogText)
//                .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dismiss();
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        intent.putExtra("isLoose", true);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//                });
//
//        return builder.create();
    }
}