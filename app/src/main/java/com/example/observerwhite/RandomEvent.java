package com.example.observerwhite;

public class RandomEvent {

    private int eventImage;
    private String eventText;
    private int moneyDecrease;
    private int happinessDecrease;
    private int foodDecrease;


    public RandomEvent(int eventImage, String eventText, int moneyDecrease, int happinessDecrease, int foodDecrease) {
        this.eventImage = eventImage;
        this.eventText = eventText;
        this.moneyDecrease = moneyDecrease;
        this.happinessDecrease = happinessDecrease;
        this.foodDecrease = foodDecrease;
    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    public int getMoneyDecrease() {
        return moneyDecrease;
    }

    public void setMoneyDecrease(int moneyDecrease) {
        this.moneyDecrease = moneyDecrease;
    }

    public int getHappinessDecrease() {
        return happinessDecrease;
    }

    public void setHappinessDecrease(int happinessDecrease) {
        this.happinessDecrease = happinessDecrease;
    }

    public int getFoodDecrease() {
        return foodDecrease;
    }

    public void setFoodDecrease(int foodDecrease) {
        this.foodDecrease = foodDecrease;
    }
}
