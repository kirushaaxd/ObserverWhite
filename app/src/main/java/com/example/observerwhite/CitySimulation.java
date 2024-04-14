package com.example.observerwhite;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.FragmentManager;

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
    private static final String KEY_UPGRADE_COIN_PRICE = "food_upgrade_coin_price";
    private static final String KEY_UPGRADE_ENTERTAINMENT_BUY_PRICE = "food_upgrade_entertainment_buy_price";
    private static final String KEY_UPGRADE_ENTERTAINMENT_MAX_PRICE = "food_upgrade_entertainment_max_price";
    private static final String KEY_UPGRADE_FOOD_BUY_PRICE = "food_upgrade_food_buy_price";
    private static final String KEY_UPGRADE_FOOD_MAX_PRICE = "food_upgrade_food_max_price";
    private static final String KEY_FOOD_UPGRADE = "food_upgrade";
    private static final String KEY_FOOD_MAX = "food_max";
    private static final String KEY_HAPPINESS_UPGRADE = "happiness_upgrade";
    private static final String KEY_HAPPINESS_MAX = "happiness_max";

    private Context mContext;
    private SharedPreferences mPrefs;
    private Handler mHandler;
    private FragmentManager fm;

    private int mMoney;
    private int mCurrentHappiness;
    private int mCurrentFood;
    private int mPopulation;
    private int mStage;
    private int mMoneyClickCoef;
    private int mHappinessMax;
    private int mFoodMax;

    private static boolean isPause;

    private TownUpgradeFragment townUpgradeFragment;
    private RandomEventFragment randomEventFragment;
    private NewGameInfoFragment newGameInfoFragment;

    public CitySimulation(Context context, FragmentManager fragmentManager, boolean isNewGame) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mHandler = new Handler(Looper.getMainLooper());
        fm = fragmentManager;
        townUpgradeFragment = new TownUpgradeFragment();
        randomEventFragment = new RandomEventFragment();
        if(isNewGame){
            isPause = true;
            newGameInfoFragment = new NewGameInfoFragment();
            newGameInfoFragment.show(fm, "NEW GAME INFO");
        }
        loadPreferences(isNewGame);
        startAutoSaveTimer();
        startPopulationGrowthTimer();
        startHappinessAndFoodDecayTimer();
        startRandomEventTimer();
    }

    private void loadPreferences(boolean isNewGame) {
        int[] upgrades = townUpgradeFragment.getUpgradeLevel();

        if(isNewGame){
            mMoney = 0;
            mCurrentHappiness = 100;
            mCurrentFood =  100;
            mPopulation = 10;
            mStage = 1;
            mMoneyClickCoef = 1;
            mHappinessMax = 100;
            mFoodMax = 100;
            townUpgradeFragment.setUpgradeCoinPrice(200);
            townUpgradeFragment.setUpgradeEntertainmentBuyPrice(30);
            townUpgradeFragment.setUpgradeEntertainmentMaxPrice(60);
            townUpgradeFragment.setUpgradeFoodBuyPrice(30);
            townUpgradeFragment.setUpgradeFoodMaxPrice(60);
            upgrades[0] = 0;
            upgrades[1] = 0;
            upgrades[2] = 0;
            upgrades[3] = 0;

        } else {
            mMoney = mPrefs.getInt(KEY_MONEY, 0);
            mCurrentHappiness = mPrefs.getInt(KEY_CURRENT_HAPPINESS, 0);
            mCurrentFood = mPrefs.getInt(KEY_CURRENT_FOOD, 0);
            mPopulation = mPrefs.getInt(KEY_POPULATION, 0);
            mStage = mPrefs.getInt(KEY_STAGE, 1);
            mMoneyClickCoef = mPrefs.getInt(KEY_MONEY_CLICK_COEF, 1);
            mHappinessMax = mPrefs.getInt(KEY_HAPPINESS_MAX_COEF, 100);
            mFoodMax = mPrefs.getInt(KEY_FOOD_MAX_COEF, 100);
            townUpgradeFragment.setUpgradeCoinPrice(mPrefs.getInt(KEY_UPGRADE_COIN_PRICE, 200));
            townUpgradeFragment.setUpgradeEntertainmentBuyPrice(mPrefs.getInt(KEY_UPGRADE_ENTERTAINMENT_BUY_PRICE, 30));
            townUpgradeFragment.setUpgradeEntertainmentMaxPrice(mPrefs.getInt(KEY_UPGRADE_ENTERTAINMENT_MAX_PRICE, 60));
            townUpgradeFragment.setUpgradeFoodBuyPrice(mPrefs.getInt(KEY_UPGRADE_FOOD_BUY_PRICE, 30));
            townUpgradeFragment.setUpgradeFoodMaxPrice(mPrefs.getInt(KEY_UPGRADE_FOOD_MAX_PRICE, 60));
            upgrades[0] = mPrefs.getInt(KEY_HAPPINESS_UPGRADE, 0);
            upgrades[1] = mPrefs.getInt(KEY_HAPPINESS_MAX, 0);
            upgrades[2] = mPrefs.getInt(KEY_FOOD_UPGRADE, 0);
            upgrades[3] = mPrefs.getInt(KEY_FOOD_MAX, 0);
        }

        townUpgradeFragment.setUpgradeLevel(upgrades);
    }

    private void startAutoSaveTimer() {
        mHandler.postDelayed(() -> {
            savePreferences();
            startAutoSaveTimer();
        }, 60000);
    }

    private void startPopulationGrowthTimer() {
        mHandler.postDelayed(() -> {
            if(isPause){
                startPopulationGrowthTimer();
                return;
            }

            if (mStage == 1) {
                mPopulation += new Random().nextInt(2) + 1;
            } else if (mStage == 2) {
                mPopulation += new Random().nextInt(4) + 7;
            }
            startPopulationGrowthTimer();
        }, 1000);
    }

    private void startRandomEventTimer(){
        mHandler.postDelayed(() -> {
            if(isPause || randomEventFragment.isAdded()){
                startRandomEventTimer();
                return;
            }

            isPause = true;
            randomEventFragment.show(fm, "RANDOM EVENT");

            startRandomEventTimer();
        }, new Random().nextInt(31000) + 30000);
    }

    private void startHappinessAndFoodDecayTimer() {
        mHandler.postDelayed(() -> {
            if(isPause){
                startHappinessAndFoodDecayTimer();
                return;
            }

            int happinessDecrease = new Random().nextInt(3);
            mCurrentHappiness -= happinessDecrease;

            if(mCurrentHappiness <= 0){
                isPause = true;
                EndGameDialog endGameDialog = new EndGameDialog();
                endGameDialog.dialogText = "Неудовлетворенность вызвало восстание горожан! Это конец.";
                endGameDialog.show(fm, "END GAME");
                return;
            }

            int foodDecrease = 2;
            mCurrentFood -= new Random().nextInt(foodDecrease);

            if(mCurrentFood <= 0){
                isPause = true;
                EndGameDialog endGameDialog = new EndGameDialog();
                endGameDialog.dialogText = "Голод вызвал восстание горожан! Это конец.";
                endGameDialog.show(fm, "END GAME");
                return;
            }

            startHappinessAndFoodDecayTimer();
        }, 1000);
    }

    public static void Unpause(){
        isPause = false;
    }

    public void savePreferences() {
        int[] upgrades = townUpgradeFragment.getUpgradeLevel();
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(KEY_MONEY, mMoney);
        editor.putInt(KEY_CURRENT_HAPPINESS, mCurrentHappiness);
        editor.putInt(KEY_CURRENT_FOOD, mCurrentFood);
        editor.putInt(KEY_POPULATION, mPopulation);
        editor.putInt(KEY_STAGE, mStage);
        editor.putInt(KEY_MONEY_CLICK_COEF, mMoneyClickCoef);
        editor.putInt(KEY_HAPPINESS_MAX_COEF, mHappinessMax);
        editor.putInt(KEY_FOOD_MAX_COEF, mFoodMax);
        editor.putInt(KEY_UPGRADE_COIN_PRICE, townUpgradeFragment.getUpgradeCoinPrice());
        editor.putInt(KEY_UPGRADE_ENTERTAINMENT_BUY_PRICE, townUpgradeFragment.getUpgradeEntertainmentBuyPrice());
        editor.putInt(KEY_UPGRADE_ENTERTAINMENT_MAX_PRICE, townUpgradeFragment.getUpgradeEntertainmentMaxPrice());
        editor.putInt(KEY_UPGRADE_FOOD_BUY_PRICE, townUpgradeFragment.getUpgradeFoodBuyPrice());
        editor.putInt(KEY_UPGRADE_FOOD_MAX_PRICE, townUpgradeFragment.getUpgradeFoodMaxPrice());
        editor.putInt(KEY_HAPPINESS_UPGRADE, upgrades[0]);
        editor.putInt(KEY_HAPPINESS_MAX, upgrades[1]);
        editor.putInt(KEY_FOOD_UPGRADE, upgrades[2]);
        editor.putInt(KEY_FOOD_MAX, upgrades[3]);
        editor.apply();
    }



    private void setUpgradeClickListeners(){
        townUpgradeFragment.binding.upgradeCoinPerClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = townUpgradeFragment.getUpgradeCoinPrice();
                if (mMoney >= price){
                    mMoneyClickCoef += 1;
                    mMoney -= price;
                    townUpgradeFragment.setUpgradeCoinPrice((int) (price * 2.5));
                    townUpgradeFragment.updateView();
                }
            }
        });

        townUpgradeFragment.binding.upgradeEntertainmentBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = townUpgradeFragment.getUpgradeEntertainmentBuyPrice();
                if (mMoney >= price){
                    mCurrentHappiness += 25;

                    if(mCurrentHappiness > mHappinessMax)
                        mCurrentHappiness = mHappinessMax;

                    mMoney -= price;
                    townUpgradeFragment.setUpgradeEntertainmentBuyPrice((int) (price * 1.05));

                    int[] upgrades = townUpgradeFragment.getUpgradeLevel();
                    upgrades[0]++;
                    townUpgradeFragment.setUpgradeLevel(upgrades);

                    townUpgradeFragment.updateView();
                }
            }
        });

        townUpgradeFragment.binding.upgradeEntertainmentMaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = townUpgradeFragment.getUpgradeEntertainmentMaxPrice();
                if (mMoney >= price){
                    mHappinessMax += 15;
                    mMoney -= price;
                    townUpgradeFragment.setUpgradeEntertainmentMaxPrice((int) (price * 1.05));

                    int[] upgrades = townUpgradeFragment.getUpgradeLevel();
                    upgrades[1]++;
                    townUpgradeFragment.setUpgradeLevel(upgrades);

                    townUpgradeFragment.updateView();
                }
            }
        });

        townUpgradeFragment.binding.upgradeFoodBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = townUpgradeFragment.getUpgradeFoodBuyPrice();
                if (mMoney >= price){
                    mCurrentFood += 20;

                    if(mCurrentFood > mFoodMax)
                        mCurrentFood = mFoodMax;

                    mMoney -= price;
                    townUpgradeFragment.setUpgradeFoodBuyPrice((int) (price * 1.05));

                    int[] upgrades = townUpgradeFragment.getUpgradeLevel();
                    upgrades[2]++;
                    townUpgradeFragment.setUpgradeLevel(upgrades);

                    townUpgradeFragment.updateView();
                }
            }
        });

        townUpgradeFragment.binding.upgradeFoodMaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = townUpgradeFragment.getUpgradeFoodMaxPrice();
                if (mMoney >= price){
                    mFoodMax += 15;
                    mMoney -= price;
                    townUpgradeFragment.setUpgradeFoodMaxPrice((int) (price * 1.05));

                    int[] upgrades = townUpgradeFragment.getUpgradeLevel();
                    upgrades[3]++;
                    townUpgradeFragment.setUpgradeLevel(upgrades);

                    townUpgradeFragment.updateView();
                }
            }
        });
    }

    public void showUpgrades(){
        if(!townUpgradeFragment.isAdded()) {
            townUpgradeFragment.show(fm, "townUpgrades");
            isPause = true;
        } else {
            isPause = false;
        }
    }

    public void createClickListeners(){
        setUpgradeClickListeners();
    }

    public void createRandomEventClickListener(int money, int happiness, int food) {
        randomEventFragment.binding.okEventBtn.setOnClickListener(v -> {
            mMoney += money;
            mCurrentHappiness += happiness;
            mCurrentFood += food;
            isPause = false;
            randomEventFragment.dismiss();
        });
    }

    public void createInfoNewGameClickListener() {
        newGameInfoFragment.binding.infoOkBtn.setOnClickListener(v -> {
            newGameInfoFragment.dismiss();
            isPause = false;
        });
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
        return mHappinessMax;
    }

    public void setHappinessMaxCoef(int happinessMaxCoef) {
        mHappinessMax = happinessMaxCoef;
    }

    public double getFoodMaxCoef() {
        return mFoodMax;
    }

    public void setFoodMaxCoef(int foodMaxCoef) {
        mFoodMax = foodMaxCoef;
    }
}

