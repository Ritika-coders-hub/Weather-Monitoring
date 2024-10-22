The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

This project is a Real-Time Data Processing System designed to monitor weather conditions using the OpenWeatherMap API. The system continuously retrieves and processes weather data for major metropolitan cities in India. Key features include daily weather summaries, alerting thresholds, and visualizations.

Features:
Weather Data Fetching:
Continuously fetches real-time weather data for cities like Delhi, Mumbai, Chennai, Bangalore, Kolkata, and Hyderabad.

Rollups and Aggregates:
Calculates daily aggregates such as average, minimum, and maximum temperature.

Determines the dominant weather condition of the day.

Alerting System:
Allows configurable thresholds (e.g., alerts for temperatures exceeding 35°C) and triggers alerts when conditions are met.

Temperature Conversion: Converts temperatures from Kelvin to Celsius or Fahrenheit based on user preference.

Data Visualization: Displays daily weather summaries and historical trends.
![Weather-Forecast-Mobile-App](https://github.com/user-attachments/assets/8ecec641-eec2-490b-9342-1084321130f6)


How It Works:

Data Collection: The system fetches data from OpenWeatherMap at configurable intervals.

Daily Summaries: Aggregates weather data daily, calculating average, maximum, minimum temperatures, and dominant weather conditions.

Alerting: Users can set temperature or weather condition thresholds, triggering notifications when limits are breached.

User Interface: A simple GUI allows users to input cities and view real-time weather updates.




Tech Stack:
Java for core logic and GUI implementation.
OpenWeatherMap API for weather data.
JSON Parsing to process API responses.
Swing for the graphical user interface (GUI).
