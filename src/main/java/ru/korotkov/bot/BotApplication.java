package ru.korotkov.bot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BotApplication extends TelegramLongPollingBot {

    String messageid;

    public static void main(String[] args) throws TelegramApiRequestException {
        //SpringApplication.run(BotApplication.class, args);
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(new BotApplication());
    }

    public void setBatton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("погода"));
        keyboardRow.add(new KeyboardButton("Нет"));

        KeyboardRow keyboardRowSecond = new KeyboardRow();
        keyboardRowSecond.add(new KeyboardButton("Чё?"));
        keyboardRowSecond.add(new KeyboardButton("Ага, прям щас"));

        keyboardRowList.add(keyboardRow);
        keyboardRowList.add(keyboardRowSecond);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void sendAnswer(Message message, String answer) throws TelegramApiException, IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        if (message.getText().equals("привет")) setBatton(sendMessage);
        if (sendMessage.getChatId().equals(messageid)){
            execute(sendMessage.setText(new Weather().getWeather(message.getText())));
        }  else{execute(sendMessage.setText(answer));}
        if (message.getText().equals("погода")) messageid = message.getChatId().toString();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            switch (message.getText().toLowerCase()) {
                case "привет":
                    try {
                        sendAnswer(message, "И тебе не кашлять");
                    } catch (TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "погода":
                    try {
                        //sendAnswer(message,new Weather().getWeather());
                        sendAnswer(message, "укажите город");
                    } catch (TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        sendAnswer(message, "");
                    } catch (TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "MyBotTest";
    }

    @Override
    public String getBotToken() {
        return "1394434441:AAHpWkvSEIbMeAz6AkIhNArBJGpyDzi1jto";
    }

}
