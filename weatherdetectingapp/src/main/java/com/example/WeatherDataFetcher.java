package com.example;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherDataFetcher {
    private static final String API_KEY = "6ec5ebbcd5d14ba404a7d311b5050e58"; // Replace with your API key
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static JSONObject getWeatherData(String cityName) {
        String urlString = API_URL + cityName + "&appid=" + API_KEY;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder inline = new StringBuilder();
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();
                return new JSONObject(inline.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert temperature from Kelvin to Celsius
    public static double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
