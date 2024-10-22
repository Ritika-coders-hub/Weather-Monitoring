package com.example;

public class WeatherData {
    private double temperature;
    private String condition;

    public WeatherData(double temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "Temperature: " + temperature + "°C, Condition: " + condition;
    }
}
