package com.example.observerwhite;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.observerwhite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean nextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!AudioService.isRunning) {
            AudioService.create(this);
            AudioService.isRunning = true;
        }
        else{
            AudioService.player.start();
        }

        nextLevel = false;

        boolean isLoose = getIntent().getBooleanExtra("isLoose", false);

        binding.continueBtn.setOnClickListener(v -> {
            startGame(isLoose);
        });

        binding.newGameBtn.setOnClickListener(v -> {
            startGame(true);
        });

        binding.quitBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void startGame(boolean isNewGame) {
        Intent intent = new Intent(this, TownGrowthActivity.class);
        intent.putExtra("isNewGame", isNewGame);
        nextLevel = true;
        startActivity(intent);
        finish();
    }

    public void onPause(){
        if (!nextLevel)
            AudioService.player.pause();
        super.onPause();
    }

    public void onDestroy(){
        if (!nextLevel)
            AudioService.player.pause();
        super.onDestroy();
    }

    public void onResume(){
        AudioService.player.start();
        super.onResume();
    }
}