package com.example.observerwhite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.observerwhite.databinding.FragmentRandomEventBinding;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.Random;

public class RandomEventFragment extends DialogFragment {

    public FragmentRandomEventBinding binding;

    private RandomEvent randomEvent;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentRandomEventBinding.inflate(inflater);

        builder.setView(binding.getRoot());

        randomEvent = getRandomEvent(getContext(), new Random().nextInt(3));

        binding.eventImage.setImageResource(randomEvent.getEventImage());
        binding.eventText.setText(randomEvent.getEventText());
        TownGrowthActivity.createRandomEventClickListener(randomEvent.getMoneyDecrease(), randomEvent.getHappinessDecrease(), randomEvent.getFoodDecrease());

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CitySimulation.randomEventUnpause(randomEvent.getMoneyDecrease(), randomEvent.getHappinessDecrease(), randomEvent.getFoodDecrease());
    }

    private RandomEvent getRandomEvent(Context context, int eventType) {
        TypedArray images;
        String[] texts;
        int[] params;
        Random random;
        int randomIndex;
        int eventImageResource;
        String eventText;
        int decrease;

        switch (eventType) {
            case 0:
                images = context.getResources().obtainTypedArray(R.array.political_images);
                texts = context.getResources().getStringArray(R.array.political_texts);
                params = context.getResources().getIntArray(R.array.political_money_decreases);

                Log.i("IMAGES TEST", String.valueOf(images));

                random = new Random();
                randomIndex = random.nextInt(images.length());

                eventImageResource = images.getResourceId(randomIndex, -1);
                eventText = texts[randomIndex];
                decrease = params[randomIndex];

                return new RandomEvent(eventImageResource, eventText, decrease, 0, 0);
            case 1:
                images = context.getResources().obtainTypedArray(R.array.social_images);
                texts = context.getResources().getStringArray(R.array.social_texts);
                params = context.getResources().getIntArray(R.array.social_money_decreases);

                random = new Random();
                randomIndex = random.nextInt(images.length());

                eventImageResource = images.getResourceId(randomIndex, -1);
                eventText = texts[randomIndex];
                decrease = params[randomIndex];

                return new RandomEvent(eventImageResource, eventText, 0, decrease, 0);
            case 2:
                images = context.getResources().obtainTypedArray(R.array.natural_images);
                texts = context.getResources().getStringArray(R.array.natural_texts);
                params = context.getResources().getIntArray(R.array.natural_money_decreases);

                random = new Random();
                randomIndex = random.nextInt(images.length());

                eventImageResource = images.getResourceId(randomIndex, -1);
                eventText = texts[randomIndex];
                decrease = params[randomIndex];

                return new RandomEvent(eventImageResource, eventText, 0, 0, decrease);
        }

        return null;
    }
}