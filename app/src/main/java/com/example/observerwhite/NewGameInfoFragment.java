package com.example.observerwhite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;

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
        builder.setCancelable(true);

        binding.infoText.setText("Текст перед началом игры");

        TownGrowthActivity.createInfoNewGameClickListener();

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CitySimulation.unpause();
    }
}