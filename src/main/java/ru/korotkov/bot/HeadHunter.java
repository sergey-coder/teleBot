package ru.korotkov.bot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

public class HeadHunter {
    public static String HHunter(String area, String specialization, ModelHeadHunter modelHeadHunter) throws IOException, ParseException, TelegramApiException {
        String urlString = "https://api.hh.ru/vacancies?area=" + area + "&text=" + specialization;
        Log log = LogFactory.getLog(HeadHunter.class);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder rezult = new StringBuilder();
        URL url = new URL(urlString);
        Scanner in = new Scanner((InputStream) url.getContent());
        while (in.hasNext()) {
            stringBuilder.append(in.nextLine());
        }
        in.close();

        try {
            JSONObject jsonObjectMain = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObjectMain.getJSONArray("items");
            if (jsonArray.length() != 0) {
                for (int i = 0; i < 1; i++) {
                    JSONObject vacanse = jsonArray.getJSONObject(i);

                    modelHeadHunter.setId(vacanse.getString("id"));

                    if (checNullObject(vacanse.get("name"))) {
                        modelHeadHunter.setNameVakanse(vacanse.getString("name"));
                    } else {
                        modelHeadHunter.setNameVakanse(null);
                    }

                    if (checNullObject(vacanse.getJSONObject("area").get("name"))) {
                        modelHeadHunter.setNameCiti(vacanse.getJSONObject("area").getString("name"));
                    } else {
                        modelHeadHunter.setNameCiti(null);
                    }

                    if (checNullObject(vacanse.get("salary")) && checNullObject(vacanse.getJSONObject("salary").get("from"))) {
                        modelHeadHunter.setSalaryFrom(String.valueOf(vacanse.getJSONObject("salary").getInt("from")));
                    } else {
                        modelHeadHunter.setSalaryFrom(null);
                    }

                    if (checNullObject(vacanse.get("salary")) && checNullObject(vacanse.getJSONObject("salary").get("to"))) {
                        modelHeadHunter.setSalaryTo(String.valueOf(vacanse.getJSONObject("salary").getInt("to")));
                    } else modelHeadHunter.setSalaryTo(null);

                    if (checNullObject(vacanse.get("salary"))) {
                        modelHeadHunter.setSalaryCurrency(vacanse.getJSONObject("salary").getString("currency"));
                    } else modelHeadHunter.setSalaryCurrency(null);

                    if (checNullObject(vacanse.getJSONObject("employer").get("name"))) {
                        modelHeadHunter.setNameEmployer(vacanse.getJSONObject("employer").getString("name"));
                    } else {
                        modelHeadHunter.setNameEmployer(null);
                    }

                    if (checNullObject(vacanse.getJSONObject("snippet").get("requirement"))) {
                        modelHeadHunter.setRequirement(vacanse.getJSONObject("snippet").getString("requirement"));
                    } else {
                        modelHeadHunter.setRequirement(null);
                    }

                    if (checNullObject(vacanse.getJSONObject("snippet").get("responsibility"))) {
                        modelHeadHunter.setResponsibility(vacanse.getJSONObject("snippet").getString("responsibility"));
                    } else {
                        modelHeadHunter.setResponsibility(null);
                    }
                    modelHeadHunter.setPublished_at(vacanse.getString("published_at"));

                    rezult.append("Вакансия " + (i + 1) + "\n\n");
                    rezult.append("https://novosibirsk.hh.ru/vacancy/" + modelHeadHunter.getId() + "\n");
                    rezult.append("Наименование вакансии : " + modelHeadHunter.getNameVakanse() + "\n");
                    rezult.append("Город : " + modelHeadHunter.getNameCiti() + "\n");
                    if (modelHeadHunter.getSalaryFrom() != null)
                        rezult.append("Заработная плата, от : " + modelHeadHunter.getSalaryFrom());
                    if (modelHeadHunter.getSalaryTo() != null) rezult.append("  до : " + modelHeadHunter.getSalaryTo());
                    if (modelHeadHunter.getSalaryCurrency() != null)
                        rezult.append(" " + modelHeadHunter.getSalaryCurrency() + "\n");
                    rezult.append("Наименование работодателя : " + modelHeadHunter.getNameEmployer() + "\n");
                    rezult.append("Опубликовано : " + modelHeadHunter.getPublished_at() + "\n");
                    rezult.append("Требования : " + modelHeadHunter.getRequirement() + "\n");
                    if (modelHeadHunter.getRequirement() != null)
                        rezult.append("Обязанности : " + modelHeadHunter.getResponsibility() + "\n\n");

                    log.debug(vacanse.getJSONObject("snippet").get("responsibility"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (rezult.length() == 0) {
            return "по данному ключевому слову вакансий не найдено";
        } else return rezult.toString();
    }

    public static boolean checNullObject(Object jsonObject) {
        if (jsonObject.getClass().getName().equals("org.json.JSONObject$Null")) {
            return false;
        } else return true;
    }
}



