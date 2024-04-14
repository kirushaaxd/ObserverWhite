package com.example.observerwhite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.observerwhite.databinding.FragmentTownUpgradeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TownUpgradeFragment extends BottomSheetDialogFragment {
    public FragmentTownUpgradeBinding binding;
    private int upgradeCoinPrice;
    private int upgradeEntertainmentBuyPrice;
    private int upgradeEntertainmentMaxPrice;
    private int upgradeFoodBuyPrice;
    private int upgradeFoodMaxPrice;

    private String[] entertainmentBuyUpgrades = new String[]{
            "Провести ярмарку", "Цирковое представление", "Городские игры",
            "Театральное представление", "Музыкальный фестиваль", "День города"
    };
    private String[] entertainmentMaxUpgrades = new String[]{
            "Построить цирк", "Построить театр", "Построить арену",
            "Построить площадь", "Парк развлечений", "Построить сцену"
    };
    private String[] foodBuyUpgrades = new String[]{
            "Собрать пшеницу", "Сделать хлеб", "Развести коров",
            "Развести свиней", "Собрать картофель", "Устроить охоту",
            "Собрать мед", "Ловить рыбу"
    };
    private String[] foodMaxUpgrades = new String[]{
            "Построить ферму", "Сделать загон", "Построить мельницу",
            "Построить хлев", "Хижина охотника", "Вспахать поле",
            "Построить пасеку", "Хижина рыболова",
    };

    private int[] upgradeLevel = new int[] { 0, 0, 0, 0 };

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

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        CitySimulation.Unpause();
    }

    public void updateView(){
        binding.upgradeCoinPerClickPrice.setText(String.valueOf(upgradeCoinPrice));
        binding.upgradeEntertainmentBuyPrice.setText(String.valueOf(upgradeEntertainmentBuyPrice));
        binding.upgradeEntertainmentMaxPrice.setText(String.valueOf(upgradeEntertainmentMaxPrice));
        binding.upgradeFoodBuyPrice.setText(String.valueOf(upgradeFoodBuyPrice));
        binding.upgradeFoodMaxPrice.setText(String.valueOf(upgradeFoodMaxPrice));

        binding.upgradeEntertainmentBuyText.setText(entertainmentBuyUpgrades[upgradeLevel[0] % 6]);
        binding.upgradeEntertainmentMaxText.setText(entertainmentMaxUpgrades[upgradeLevel[1] % 6]);
        binding.upgradeFoodBuyText.setText(foodBuyUpgrades[upgradeLevel[2] % 8]);
        binding.upgradeFoodMaxText.setText(foodMaxUpgrades[upgradeLevel[3] % 8]);
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
