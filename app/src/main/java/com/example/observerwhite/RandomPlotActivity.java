package com.example.observerwhite;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.observerwhite.databinding.ActivityRandomPlotBinding;

public class RandomPlotActivity extends AppCompatActivity {

    private ActivityRandomPlotBinding binding;

    private String[] messages;
    private String[] answers;
    private String[] parameters;
    private int chapterLength;
    private int pos;
    private int chapter;
    private boolean nextLevel;


    private void LoadChapterInfo(int chapter){
        switch (chapter) {
            case (2):
                messages = getResources().getStringArray(R.array.chapter_2_messages);
                answers = getResources().getStringArray(R.array.chapter_2_answers);
                parameters = getResources().getStringArray(R.array.chapter_2_parameters);
                break;
            case (3):
                messages = getResources().getStringArray(R.array.chapter_3_messages);
                answers = getResources().getStringArray(R.array.chapter_3_answers);
                parameters = getResources().getStringArray(R.array.chapter_3_parameters);
                break;
            case (4):
                messages = getResources().getStringArray(R.array.chapter_4_messages);
                answers = getResources().getStringArray(R.array.chapter_4_answers);
                parameters = getResources().getStringArray(R.array.chapter_4_parameters);
                break;
        }
        chapterLength = messages.length;
        pos = 0;
        nextStep();
    }

    private void process(int i){
        binding.text.setText(messages[i]);

        String[] params = parameters[i].split("/");
        binding.eventImageBack.setImageResource(getResources().getIdentifier(params[0], "drawable", this.getPackageName()));
        binding.eventImageFront.setImageResource(getResources().getIdentifier(params[1], "drawable", this.getPackageName()));
        String[] answ = answers[i].split("/");
        if (parseInt(answ[0]) == 1){
            binding.firstBtn.setVisibility(View.INVISIBLE);
            binding.secondBtn.setText(answ[1]);
        }
        else
        {
            binding.firstBtn.setVisibility(View.VISIBLE);
            binding.firstBtn.setText(answ[1]);
            binding.secondBtn.setText(answ[2]);

        }

    }

    private void nextStep(){
        if (pos < chapterLength){
            process(pos);
            pos++;
        }
        else if (chapter < 4){
            chapter++;
            saveChapter();
            Toast.makeText(this, "Новая глава", Toast.LENGTH_SHORT).show();
            LoadChapterInfo(chapter);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("isLoose", true);
            AudioService.isRunning = false;
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nextLevel = false;
        binding = ActivityRandomPlotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AudioService.create2(this);

        SharedPreferences sp = getSharedPreferences("CityPrefs", Context.MODE_PRIVATE);
        chapter = sp.getInt("stage", 2);

        binding.firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });

        binding.secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    public void saveChapter(){
        SharedPreferences sp = getSharedPreferences("CityPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("stage", chapter);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioService.player.start();
        LoadChapterInfo(chapter);
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