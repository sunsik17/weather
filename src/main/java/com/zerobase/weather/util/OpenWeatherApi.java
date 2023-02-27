package com.zerobase.weather.util;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenWeatherApi {

    @Value("${openWeather.apikey}")
    private String apiKey;
    @Value("${openWeather.url}")
    private String apiUrl;

    public Map<String, Object> getNowWeatherMap() {
        Gson gson = new Gson();
        JSONObject jsonObject;

        String weatherString = getWeatherString();
        System.out.println(weatherString);
        jsonObject = gson.fromJson(weatherString, JSONObject.class);

        Map<String, Object> resultMap = new HashMap<>();

        JSONArray jsonArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) jsonArray.get(0);

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }

    private String getWeatherString() {
        try {
            URL url = new URL(apiUrl + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException(readApiStream((connection.getErrorStream())));
            }

            return readApiStream(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String readApiStream(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        return response.toString();
    }
}
