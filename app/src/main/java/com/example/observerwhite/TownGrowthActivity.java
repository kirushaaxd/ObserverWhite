package com.example.observerwhite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.example.observerwhite.databinding.ActivityTownGrowthBinding;

public class TownGrowthActivity extends AppCompatActivity {

    private ActivityTownGrowthBinding binding;
    private static CitySimulation citySimulation;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean nextLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityTownGrowthBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_town_growth);
        setContentView(binding.getRoot());

        nextLevel = false;

        Intent intent = getIntent();
        boolean isNewGame = intent.getBooleanExtra("isNewGame", true);

        citySimulation = new CitySimulation(this, getSupportFragmentManager(), this, isNewGame);

        binding.moneyText.setText(String.valueOf(citySimulation.getMoney()));

        binding.happinessText.setText(String.valueOf(citySimulation.getCurrentHappiness()));
        binding.foodText.setText(String.valueOf(citySimulation.getCurrentFood()));
        binding.populationText.setText(String.valueOf(citySimulation.getPopulation()));

        if (citySimulation.getPopulation() < 4000)
            binding.townImg.setImageResource(R.drawable.small_village);
        else
            binding.townImg.setImageResource(R.drawable.village);

        binding.increaseMoneyBtn.setOnClickListener(v -> {
            int currentMoney = citySimulation.getMoney();
            int moneyCoef = citySimulation.getMoneyClickCoef();
            int newMoney = currentMoney + moneyCoef;
            citySimulation.setMoney(newMoney);
            binding.moneyText.setText(String.valueOf(newMoney));
        });

        binding.backToMenuBtn.setOnClickListener(v -> {
            citySimulation.savePreferences();
            Intent intent1 = new Intent(this, MainActivity.class);
            nextLevel = true;
            citySimulation.mHandler.removeCallbacksAndMessages(null);
            startActivity(intent1);
            finish();
        });

        binding.buyUpgradesBtn.setOnClickListener(v -> {
            citySimulation.showUpgrades();
        });

        updateUI();
    }

    public static void createClickListenersUpgrades(){
        citySimulation.createClickListeners();
    }

    public static void createRandomEventClickListener(int money, int happiness, int food){
        citySimulation.createRandomEventClickListener(money, happiness, food);
    }

    public static void createInfoNewGameClickListener(){
        citySimulation.createInfoNewGameClickListener();
    }

    private void updateUI() {
        mHandler.postDelayed(() -> {
            if (citySimulation.getPopulation() > 4000)
                binding.townImg.setImageResource(R.drawable.village);
            binding.moneyText.setText(String.valueOf(citySimulation.getMoney()));
            binding.happinessText.setText(String.valueOf(citySimulation.getCurrentHappiness()));
            binding.foodText.setText(String.valueOf(citySimulation.getCurrentFood()));
            binding.populationText.setText(String.valueOf(citySimulation.getPopulation()));

            updateUI();
        }, 1000);
    }

    public void onStart(){
        AudioService.player.start();
        super.onStart();
    }

    public void onPause(){
        if (!nextLevel && !AudioService.changeChapter)
            AudioService.player.pause();
        super.onPause();
    }

    public void onDestroy(){
        if (!nextLevel && !AudioService.changeChapter)
            AudioService.player.pause();
        super.onDestroy();
    }

    public void onResume(){
        AudioService.player.start();
        super.onResume();
    }
}