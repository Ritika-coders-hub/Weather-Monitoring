package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WeatherDataFetcherGUI extends JFrame {

    private JTextField cityInput, thresholdInput;
    private JTextArea outputArea;
    private List<WeatherData> weatherDataList = new ArrayList<>();
    private double temperatureThreshold = 35.0; // Default threshold

    public WeatherDataFetcherGUI() {
        setTitle("Weather Monitoring System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel cityLabel = new JLabel("Enter City:");
        cityInput = new JTextField(15);

        JLabel thresholdLabel = new JLabel("Temp Threshold (°C):");
        thresholdInput = new JTextField(5);
        thresholdInput.setText(String.valueOf(temperatureThreshold));

        JButton fetchButton = new JButton("Fetch Weather");

        inputPanel.add(cityLabel);
        inputPanel.add(cityInput);
        inputPanel.add(thresholdLabel);
        inputPanel.add(thresholdInput);
        inputPanel.add(fetchButton);

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Fetch weather data on button click
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = cityInput.getText();
                try {
                    temperatureThreshold = Double.parseDouble(thresholdInput.getText());
                } catch (NumberFormatException ex) {
                    outputArea.append("Invalid threshold input. Using default (35°C).\n");
                }

                JSONObject weatherDataJson = WeatherDataFetcher.getWeatherData(cityName);
                if (weatherDataJson != null) {
                    double tempKelvin = weatherDataJson.getJSONObject("main").getDouble("temp");
                    double tempCelsius = WeatherDataFetcher.convertKelvinToCelsius(tempKelvin);
                    String weatherCondition = weatherDataJson.getJSONArray("weather").getJSONObject(0)
                            .getString("main");

                    WeatherData weatherData = new WeatherData(tempCelsius, weatherCondition);
                    weatherDataList.add(weatherData);
                    displayWeatherData(weatherData);
                    checkThresholdAlert(weatherData);
                }
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button to show the daily summary
        JButton summaryButton = new JButton("Show Daily Summary");
        summaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDailySummary();
            }
        });

        add(summaryButton, BorderLayout.SOUTH);
    }

    // Display weather data in the text area
    public void displayWeatherData(WeatherData weatherData) {
        outputArea.append("City: " + cityInput.getText() + "\n");
        outputArea.append("Temperature (Celsius): " + weatherData.getTemperature() + "\n");
        outputArea.append("Weather Condition: " + weatherData.getCondition() + "\n");
        outputArea.append("---------------------------------\n");
    }

    // Check if temperature exceeds the threshold
    public void checkThresholdAlert(WeatherData weatherData) {
        if (weatherData.getTemperature() > temperatureThreshold) {
            outputArea.append("Alert: Temperature exceeded " + temperatureThreshold + "°C!\n");
            // You can add more alerting logic (email, sound, etc.)
        }
    }

    // Calculate daily summary and store to file
    public void calculateDailySummary() {
        if (weatherDataList.isEmpty()) {
            outputArea.append("No data available for summary.\n");
            return;
        }

        double sumTemp = 0;
        double maxTemp = Double.MIN_VALUE;
        double minTemp = Double.MAX_VALUE;
        String dominantCondition = "";
        int clearCount = 0, rainCount = 0, snowCount = 0;

        for (WeatherData data : weatherDataList) {
            double temp = data.getTemperature();
            sumTemp += temp;
            if (temp > maxTemp)
                maxTemp = temp;
            if (temp < minTemp)
                minTemp = temp;

            String condition = data.getCondition();
            if (condition.equals("Clear"))
                clearCount++;
            if (condition.equals("Rain"))
                rainCount++;
            if (condition.equals("Snow"))
                snowCount++;
        }

        double avgTemp = sumTemp / weatherDataList.size();
        dominantCondition = (clearCount >= rainCount && clearCount >= snowCount) ? "Clear"
                : (rainCount >= clearCount && rainCount >= snowCount) ? "Rain" : "Snow";

        outputArea.append("Daily Summary:\n");
        outputArea.append("Average Temperature: " + avgTemp + "°C\n");
        outputArea.append("Max Temperature: " + maxTemp + "°C\n");
        outputArea.append("Min Temperature: " + minTemp + "°C\n");
        outputArea.append("Dominant Weather Condition: " + dominantCondition + "\n");
        outputArea.append("---------------------------------\n");

        saveSummaryToFile(avgTemp, maxTemp, minTemp, dominantCondition);
        weatherDataList.clear(); // Clear data for the next day (optional)
    }

    // Save the summary to a file (simulating a database)
    public void saveSummaryToFile(double avgTemp, double maxTemp, double minTemp, String condition) {
        try (FileWriter fileWriter = new FileWriter("daily_weather_summary.txt", true);
                PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println("Average Temperature: " + avgTemp + "°C");
            writer.println("Max Temperature: " + maxTemp + "°C");
            writer.println("Min Temperature: " + minTemp + "°C");
            writer.println("Dominant Weather Condition: " + condition);
            writer.println("---------------------------------");
        } catch (IOException e) {
            outputArea.append("Error writing summary to file.\n");
        }
    }

    public static void main(String[] args) {
        WeatherDataFetcherGUI app = new WeatherDataFetcherGUI();
        app.setVisible(true);
    }
}
