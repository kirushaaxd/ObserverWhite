package com.example.observerwhite;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;

public class CitySimulation {
    private static final String PREFS_NAME = "CityPrefs";
    private static final String KEY_MONEY = "money";
    private static final String KEY_CURRENT_HAPPINESS = "current_happiness";
    private static final String KEY_CURRENT_FOOD = "current_food";
    private static final String KEY_POPULATION = "population";
    private static final String KEY_STAGE = "stage";
    private static final String KEY_MONEY_CLICK_COEF = "money_click_coef";
    private static final String KEY_HAPPINESS_MAX_COEF = "happiness_max_coef";
    private static final String KEY_FOOD_MAX_COEF = "food_max_coef";

    private Context mContext;
    private SharedPreferences mPrefs;
    private Handler mHandler;

    private int mMoney;
    private int mCurrentHappiness;
    private int mCurrentFood;
    private int mPopulation;
    private int mStage;
    private int mMoneyClickCoef;
    private int mHappinessMaxCoef;
    private int mFoodMaxCoef;

    public CitySimulation(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mHandler = new Handler(Looper.getMainLooper());
        loadPreferences();
        startAutoSaveTimer();
        startPopulationGrowthTimer();
        startHappinessAndFoodDecayTimer();
    }

    private void loadPreferences() {
        mMoney = mPrefs.getInt(KEY_MONEY, 0);
        mCurrentHappiness = mPrefs.getInt(KEY_CURRENT_HAPPINESS, 0);
        mCurrentFood = mPrefs.getInt(KEY_CURRENT_FOOD, 0);
        mPopulation = mPrefs.getInt(KEY_POPULATION, 0);
        mStage = mPrefs.getInt(KEY_STAGE, 1);
        mMoneyClickCoef = mPrefs.getInt(KEY_MONEY_CLICK_COEF, 1);
        mHappinessMaxCoef = mPrefs.getInt(KEY_HAPPINESS_MAX_COEF, 1);
        mFoodMaxCoef = mPrefs.getInt(KEY_FOOD_MAX_COEF, 1);
    }

    private void startAutoSaveTimer() {
        mHandler.postDelayed(() -> {
            savePreferences();
            startAutoSaveTimer();
        }, 60000);
    }

    private void startPopulationGrowthTimer() {
        mHandler.postDelayed(() -> {
            if (mStage == 1) {
                mPopulation += new Random().nextInt(2) + 1;
            } else if (mStage == 2) {
                mPopulation += new Random().nextInt(4) + 7;
            }
            startPopulationGrowthTimer();
        }, 1000);
    }

    private void startHappinessAndFoodDecayTimer() {
        mHandler.postDelayed(() -> {
            int happinessDecrease = new Random().nextInt(3);
            mCurrentHappiness -= happinessDecrease;

            int foodDecrease = 2;
            mCurrentFood -= new Random().nextInt(foodDecrease);

            startHappinessAndFoodDecayTimer();
        }, 1000);
    }

    public void savePreferences() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(KEY_MONEY, mMoney);
        editor.putInt(KEY_CURRENT_HAPPINESS, mCurrentHappiness);
        editor.putInt(KEY_CURRENT_FOOD, mCurrentFood);
        editor.putInt(KEY_POPULATION, mPopulation);
        editor.putInt(KEY_STAGE, mStage);
        editor.putInt(KEY_MONEY_CLICK_COEF, mMoneyClickCoef);
        editor.putInt(KEY_HAPPINESS_MAX_COEF, mHappinessMaxCoef);
        editor.putInt(KEY_FOOD_MAX_COEF, mFoodMaxCoef);
        editor.apply();
    }

    // Getters and setters
    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int money) {
        mMoney = money;
    }

    public int getCurrentHappiness() {
        return mCurrentHappiness;
    }

    public void setCurrentHappiness(int currentHappiness) {
        mCurrentHappiness = currentHappiness;
    }

    public int getCurrentFood() {
        return mCurrentFood;
    }

    public void setCurrentFood(int currentFood) {
        mCurrentFood = currentFood;
    }

    public int getPopulation() {
        return mPopulation;
    }

    public void setPopulation(int population) {
        mPopulation = population;
    }

    public int getStage() {
        return mStage;
    }

    public void setStage(int stage) {
        mStage = stage;
    }

    public int getMoneyClickCoef() {
        return mMoneyClickCoef;
    }

    public void setMoneyClickCoef(int moneyClickCoef) {
        mMoneyClickCoef = moneyClickCoef;
    }

    public int getHappinessMaxCoef() {
        return mHappinessMaxCoef;
    }

    public void setHappinessMaxCoef(int happinessMaxCoef) {
        mHappinessMaxCoef = happinessMaxCoef;
    }

    public double getFoodMaxCoef() {
        return mFoodMaxCoef;
    }

    public void setFoodMaxCoef(int foodMaxCoef) {
        mFoodMaxCoef = foodMaxCoef;
    }
}

