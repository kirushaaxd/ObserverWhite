package com.example.observerwhite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.observerwhite.databinding.FragmentTownUpgradeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TownUpgradeFragment extends BottomSheetDialogFragment {
    public FragmentTownUpgradeBinding binding;
    private int upgradeCoinPrice;
    private int upgradeEntertainmentBuyPrice;
    private int upgradeEntertainmentMaxPrice;
    private int upgradeFoodBuyPrice;
    private int upgradeFoodMaxPrice;
    private String[] entertainmentBuyUpgrades;
    private String[] entertainmentMaxUpgrades;
    private String[] foodBuyUpgrades;
    private String[] foodMaxUpgrades;
    private int[] upgradeLevel = new int[]{0, 0, 0, 0};

    public void setStartPrice(){
        upgradeCoinPrice = 200;
        upgradeEntertainmentBuyPrice = 30;
        upgradeEntertainmentMaxPrice = 60;
        upgradeFoodBuyPrice = 30;
        upgradeFoodMaxPrice = 60;

        entertainmentBuyUpgrades = new String[]{
                "Провести ярмарку", "2", "3", "4", "5", "6"
        };
        entertainmentMaxUpgrades = new String[]{
                "Построить цирк", "2", "3", "4", "5", "6"
        };

        foodBuyUpgrades = new String[]{
                "Собрать пшеницу", "2", "3", "4", "5", "6"
        };

        foodMaxUpgrades = new String[]{
                "Построить ферму", "2", "3", "4", "5", "6"
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTownUpgradeBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
        TownGrowthActivity.createClickListenersUpgrades();
    }

    public void updateView(){
        binding.upgradeCoinPerClickPrice.setText(String.valueOf(upgradeCoinPrice));
        binding.upgradeEntertainmentBuyPrice.setText(String.valueOf(upgradeEntertainmentBuyPrice));
        binding.upgradeEntertainmentMaxPrice.setText(String.valueOf(upgradeEntertainmentMaxPrice));
        binding.upgradeFoodBuyPrice.setText(String.valueOf(upgradeFoodBuyPrice));
        binding.upgradeFoodMaxPrice.setText(String.valueOf(upgradeFoodMaxPrice));

        binding.upgradeEntertainmentBuyText.setText(entertainmentBuyUpgrades[upgradeLevel[0] % 6]);
        binding.upgradeEntertainmentMaxText.setText(entertainmentMaxUpgrades[upgradeLevel[1] % 6]);
        binding.upgradeFoodBuyText.setText(foodBuyUpgrades[upgradeLevel[2] % 6]);
        binding.upgradeFoodMaxText.setText(foodMaxUpgrades[upgradeLevel[3] % 6]);
    }

    public int getUpgradeCoinPrice() {
        return upgradeCoinPrice;
    }

    public int getUpgradeEntertainmentBuyPrice() {
        return upgradeEntertainmentBuyPrice;
    }

    public int getUpgradeEntertainmentMaxPrice() {
        return upgradeEntertainmentMaxPrice;
    }

    public int getUpgradeFoodBuyPrice() {
        return upgradeFoodBuyPrice;
    }

    public int getUpgradeFoodMaxPrice() {
        return upgradeFoodMaxPrice;
    }

    public void setUpgradeCoinPrice(int upgradeCoinPrice) {
        this.upgradeCoinPrice = upgradeCoinPrice;
    }

    public void setUpgradeEntertainmentBuyPrice(int upgradeEntertainmentBuyPrice) {
        this.upgradeEntertainmentBuyPrice = upgradeEntertainmentBuyPrice;
    }

    public void setUpgradeEntertainmentMaxPrice(int upgradeEntertainmentMaxPrice) {
        this.upgradeEntertainmentMaxPrice = upgradeEntertainmentMaxPrice;
    }

    public void setUpgradeFoodBuyPrice(int upgradeFoodBuyPrice) {
        this.upgradeFoodBuyPrice = upgradeFoodBuyPrice;
    }

    public void setUpgradeFoodMaxPrice(int upgradeFoodMaxPrice) {
        this.upgradeFoodMaxPrice = upgradeFoodMaxPrice;
    }

    public int[] getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int[] upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
}
