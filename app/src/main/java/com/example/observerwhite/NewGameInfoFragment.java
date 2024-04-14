package com.example.observerwhite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.observerwhite.databinding.FragmentNewGameInfoBinding;

public class NewGameInfoFragment extends DialogFragment {

    public FragmentNewGameInfoBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentNewGameInfoBinding.inflate(inflater);

        builder.setView(binding.getRoot());

        binding.infoText.setText("Текст перед началом игры");

        TownGrowthActivity.createInfoNewGameClickListener();

        return builder.create();
    }
}