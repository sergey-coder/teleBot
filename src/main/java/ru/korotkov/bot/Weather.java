package ru.korotkov.bot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.Scanner;

public  class Weather {
    public static String appid = "135737d695fa1085ef39ec53ec182078";


    public static String getWeather(String citi, ModelWeather modelWeather) throws IOException {

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + citi + "&APPID=" + appid + "&units=metric&lang=ru";

        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(urlString);
        Scanner in = new Scanner((InputStream) url.getContent());
        while (in.hasNext()){
            stringBuilder.append(in.nextLine());
        }
        in.close();
        JSONObject jsonObject = new JSONObject(stringBuilder.toString());

        JSONArray weather = jsonObject.getJSONArray("weather");
        modelWeather.setMain(weather.getJSONObject(0).getString("main"));
        modelWeather.setDescription(weather.getJSONObject(0).getString("description"));
        modelWeather.setIcon((String)weather.getJSONObject(0).get("icon"));
        modelWeather.setTemp(jsonObject.getJSONObject("main").getDouble("temp"));
        modelWeather.setFeels_like(jsonObject.getJSONObject("main").getDouble("feels_like"));
        modelWeather.setPressure(jsonObject.getJSONObject("main").getDouble("pressure"));
        modelWeather.setHumidity(jsonObject.getJSONObject("main").getDouble("humidity"));
        modelWeather.setVisibility(jsonObject.getDouble("visibility"));
        if(jsonObject.has("wind"))modelWeather.setWind(jsonObject.getJSONObject("wind").getDouble("speed"));
        if(jsonObject.has("clouds"))modelWeather.setClouds(jsonObject.getJSONObject("clouds").getDouble("all"));
        if (jsonObject.has("rain"))modelWeather.setRain(jsonObject.getJSONObject("rain").getDouble("1h"));
        if(jsonObject.has("snow"))modelWeather.setSnow(jsonObject.getJSONObject("snow").getDouble("1h"));
        modelWeather.setName(jsonObject.getString("name"));

        StringBuilder rezultWeather = new StringBuilder();
        rezultWeather.append("http://openweathermap.org/img/w/" + modelWeather.getIcon() + ".png \n");
        rezultWeather.append("Погода на сегодня в городе :" + modelWeather.getName() + "\n");
        rezultWeather.append("Общее описание : " + modelWeather.getDescription() + "\n");
        rezultWeather.append("температура :" + modelWeather.getTemp() + "градусов" + "\n");
        rezultWeather.append("температура по ощущениям :" + modelWeather.getFeels_like()+ "градусов" + "\n");
        rezultWeather.append("давление :" + modelWeather.getPressure() + "\n");
        rezultWeather.append("влажность :" + modelWeather.getHumidity() + "%" + "\n");
        rezultWeather.append("видимость :" + modelWeather.getVisibility() +"метров" + "\n");
        if(modelWeather.getWind()!=null)rezultWeather.append("ветер :" + modelWeather.getWind()+ "м/с" + "\n");
        if(modelWeather.getClouds()!=null)rezultWeather.append("облачность :" + modelWeather.getClouds()+ "%" + "\n");
        if(modelWeather.getRain()!=null)rezultWeather.append("дождь :" + modelWeather.getRain()+ "мм" + "\n");
        if(modelWeather.getSnow()!=null)rezultWeather.append("снег :" + modelWeather.getSnow() + "мм"+ "\n");
        return rezultWeather.toString();

    }
}
