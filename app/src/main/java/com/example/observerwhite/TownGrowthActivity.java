package com.example.observerwhite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.observerwhite.databinding.ActivityTownGrowthBinding;

public class TownGrowthActivity extends AppCompatActivity {

    private ActivityTownGrowthBinding binding;
    private static CitySimulation citySimulation;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownGrowthBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_town_growth);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        boolean isNewGame = intent.getBooleanExtra("isNewGame", true);

        citySimulation = new CitySimulation(this);

        if (isNewGame) {
            citySimulation.setStage(1);
            citySimulation.setMoney(0);
            citySimulation.setPopulation(10);
            citySimulation.setCurrentHappiness(100);
            citySimulation.setCurrentFood(100);

            binding.moneyText.setText(String.valueOf(0));
        } else {
            binding.moneyText.setText(String.valueOf(citySimulation.getMoney()));
        }

        binding.happinessText.setText(String.valueOf(citySimulation.getCurrentHappiness()));
        binding.foodText.setText(String.valueOf(citySimulation.getCurrentFood()));

        binding.increaseMoneyBtn.setOnClickListener(v -> {
            int currentMoney = citySimulation.getMoney();
            int moneyCoef = citySimulation.getMoneyClickCoef();
            int newMoney = currentMoney + moneyCoef;
            citySimulation.setMoney(newMoney);
            binding.moneyText.setText(String.valueOf(newMoney));
        });

        binding.backToMenuBtn.setOnClickListener(v -> {
            citySimulation.savePreferences();
            finish();
        });

        binding.buyUpgradesBtn.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            citySimulation.showUpgrades(fm);
        });

        updateUI();
    }

    public static void createClickListenersUpgrades(){
        citySimulation.createClickListeners();
    }

    private void updateUI() {
        mHandler.postDelayed(() -> {
            binding.moneyText.setText(String.valueOf(citySimulation.getMoney()));
            binding.happinessText.setText(String.valueOf(citySimulation.getCurrentHappiness()));
            binding.foodText.setText(String.valueOf(citySimulation.getCurrentFood()));
            binding.populationText.setText(String.valueOf(citySimulation.getPopulation()));

            updateUI();
        }, 1000);
    }

}