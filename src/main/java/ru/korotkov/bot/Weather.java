package ru.korotkov.bot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    String appid = "135737d695fa1085ef39ec53ec182078";

    public String getWeather(String siti) throws IOException {
       // String urlString = "https://api.gismeteo.net/v2/search/cities/?query=москв";
        String urlString = "https://samples.openweathermap.org/data/2.5/weather?q=" + siti + "&appid=" + appid + "&units=metric&lang=ru";

        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(urlString);
        Scanner in = new Scanner((InputStream) url.getContent());
        while (in.hasNext()){
            stringBuilder.append(in.nextLine());
            System.out.println(in);
        }
        in.close();
        return stringBuilder.toString();
    }
}
