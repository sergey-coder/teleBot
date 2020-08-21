package ru.korotkov.bot;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModelWeather {
    String strUrl = "https://meteoinfo.ru/forecasts/russia/novosibirsk-area";
    StringBuilder content = new StringBuilder();
//<a href="/statusmrf">			О статусе информации прогнозов на неделю		</a>

    public static void main(String[] args) throws IOException {
        String strUrl = "https://api.gismeteo.net/v2/weather/current/?latitude=54.35&longitude=52.52";
        StringBuilder content = new StringBuilder();
        final URL url = new URL(strUrl);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Единичный елемент - " + in.readLine());
           /* if(inputLine.equals("href")|| (inputLine.substring(inputLine.length()-4,inputLine.length()-1)).equals("</a>")){

            }
            content.append(inputLine);*/
        }
        in.close();
        System.out.println(content.toString());
    }

   /* public void getJsonMeteo() throws IOException {
        final URL url = new URL(strUrl);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content.toString());
    }*/
}
