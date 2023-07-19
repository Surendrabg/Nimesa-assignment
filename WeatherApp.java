package com.nemesa.assignment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherApp {

   private static final String API_KEY = "YOUR_API_KEY";
   private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast/hourly?q=London&appid=" + API_KEY;

   public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int option;
            do {
                displayMenu();
                option = Integer.parseInt(reader.readLine());
                switch (option) {
                    case 1:
                        getWeather(reader);
                        break;
                    case 2:
                        getWindSpeed(reader);
                        break;
                    case 3:
                        getPressure(reader);
                        break;
                    case 0:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (option != 0);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




   private static void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get wind speed");
        System.out.println("3. Get pressure");
        System.out.println("0. Exit");
    }




   private static void getWeather(BufferedReader reader) throws IOException {
        System.out.println("Please enter the date (yyyy-MM-dd):");
        String date = reader.readLine();
        String apiUrl = API_URL + "&dt=" + date;
        JSONObject response = sendGetRequest(apiUrl);
        JSONArray weatherList = response.getJSONArray("list");
        if (weatherList.length() > 0) {
            double temperature = weatherList.getJSONObject(0).getJSONObject("main").getDouble("temp");
            System.out.println("Temperature on " + date + ": " + temperature + " K");
        } else {
            System.out.println("No weather data available for the specified date.");
        }
    }




   private static void getWindSpeed(BufferedReader reader) throws IOException {
        System.out.println("Please enter the date (yyyy-MM-dd):");
        String date = reader.readLine();
        String apiUrl = API_URL + "&dt=" + date;
        JSONObject response = sendGetRequest(apiUrl);
        JSONArray weatherList = response.getJSONArray("list");
        if (weatherList.length() > 0) {
            double windSpeed = weatherList.getJSONObject(0).getJSONObject("wind").getDouble("speed");
            System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
        } else {
            System.out.println("No weather data available for the specified date.");
        }
    }




   private static void getPressure(BufferedReader reader) throws IOException {
        System.out.println("Please enter the date (yyyy-MM-dd):");
        String date = reader.readLine();
        String apiUrl = API_URL + "&dt=" + date;
        JSONObject response = sendGetRequest(apiUrl);
        JSONArray weatherList = response.getJSONArray("list");
        if (weatherList.length() > 0) {
            double pressure = weatherList.getJSONObject(0).getJSONObject("main").getDouble("pressure");
            System.out.println("Pressure on " + date + ": " + pressure + " hPa");
        } else {
            System.out.println("No weather data available for the specified date.");
        }
    }




   private static JSONObject sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new JSONObject(response.toString());
        } else {
            System.out.println("Error response code: " + responseCode);
            return null;
        }
    }
}
