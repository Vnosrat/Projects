package com.viannnosrat.montyhall.model;

public class GameResult {

    private int numberOfIterations;
    private int numberOfWins;
    private float percentage;

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public enum StrategyEnum {

        KEEP("KEEP"),

        SWITCH("SWITCH");

        private String value;

        StrategyEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static GameResult.StrategyEnum fromValue(String text) {
            for (GameResult.StrategyEnum b : GameResult.StrategyEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    StrategyEnum gameStrategy;

    public StrategyEnum getGameStrategy() {
        return gameStrategy;
    }

    public void setGameStrategy(StrategyEnum gameStrategy) {
        this.gameStrategy = gameStrategy;
    }



}
