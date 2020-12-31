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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BotApplication extends TelegramLongPollingBot {
    public static String currentMessage = "";
    int messageid;

    public static void main(String[] args) throws TelegramApiRequestException {
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
        keyboardRow.add(new KeyboardButton("работа"));
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void sendAnswer(Message message, String answer) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        if (message.getText().equals("привет")) setBatton(sendMessage);
        execute(sendMessage.setText(answer));
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        try {
            if (message != null && message.hasText()) {
                switch (message.getText().toLowerCase()) {
                    case "привет":
                        sendAnswer(message, "И тебе не кашлять");
                        break;
                    case "погода":
                        currentMessage = "погода";
                        sendAnswer(message, "введите название города");
                        break;
                    case "работа":
                        currentMessage = "работа";
                        sendAnswer(message, "введите ключевое слово для поиска");
                        break;
                    default:
                        if (currentMessage.equals("погода")) {
                            currentMessage = "";
                            sendAnswer(message, Weather.getWeather(message.getText(), new ModelWeather()));
                            sendAnswer(message, "для введенного наименования данных о погоде не имеется");
                            break;
                        } else if (currentMessage.equals("работа")) {
                            currentMessage = "";
                            HeadHunter.HHunter("4", message.getText(), new ModelHeadHunter());
                            sendAnswer(message, HeadHunter.HHunter("4", message.getText(), new ModelHeadHunter()));
                            sendAnswer(message, "для введенного наименования данных о вакансиях не имеется");
                            break;
                        } else {
                            sendAnswer(message, "к введенному сообщению привязанных методов не имеется");
                            break;
                        }
                }
            }
        } catch (TelegramApiException | ParseException | IOException e) {
            e.printStackTrace();
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
