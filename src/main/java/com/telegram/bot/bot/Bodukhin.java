package com.telegram.bot.bot;

import com.telegram.bot.config.bot.BotConfig;
import com.telegram.bot.domain.City;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class Bodukhin extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        if (message.startsWith("/add")) {
            List<String> arg = getArguments(message);

            City city = new City();
            city.setName(arg.get(0));
            city.setDescription(arg.get(1));

            String postUrl = "http://localhost:8080/city/create";
            ResponseEntity<String> responseEntity = fromBotToDb(city, postUrl, HttpMethod.POST);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonEntity = new JSONObject(responseEntity.getBody());
                String outMessage = jsonEntity.getString("name")
                        + " - " + jsonEntity.getString("description");
                sendMsg(update.getMessage().getChatId().toString(), outMessage);
            } else {
                sendMsg(update.getMessage().getChatId().toString(), "Ошибка, город уже существует!");
            }
        } else if (message.startsWith("/all")) {
            City city = new City();
            city.setName("name");
            StringBuilder outMessage = new StringBuilder();

            String getUrl = "http://localhost:8080/city/all";
            ResponseEntity<String> responseEntity = fromBotToDb(city, getUrl, HttpMethod.GET);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONArray jsonArray = new JSONArray(responseEntity.getBody());
                for (int i = 0; i < jsonArray.length(); i++) {
                    outMessage.append(jsonArray.getJSONObject(i).getString("name"))
                            .append(System.getProperty("line.separator"));
                }
                sendMsg(update.getMessage().getChatId().toString(), outMessage.toString());
            } else {
                sendMsg(update.getMessage().getChatId().toString(), "Произошла ошибка!");
            }
        } else if (message.startsWith("/update")) {
            List<String> arg = getArguments(message);

            City city = new City();
            city.setName(arg.get(0));
            city.setDescription(arg.get(1));

            String postUrl = "http://localhost:8080/city/update";
            ResponseEntity<String> responseEntity = fromBotToDb(city, postUrl, HttpMethod.POST);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonEntity = new JSONObject(responseEntity.getBody());
                String outMessage = jsonEntity.getString("name")
                        + " - " + jsonEntity.getString("description");
                sendMsg(update.getMessage().getChatId().toString(), outMessage);
            } else {
                sendMsg(update.getMessage().getChatId().toString(), "Возникла ошибка при обновлении города!");
            }
        } else if (message.startsWith("/delete")) {
            List<String> arg = getArguments(message);

            City city = new City();
            city.setName(arg.get(0));
            city.setDescription("description");

            String deleteUrl = "http://localhost:8080/city/delete";
            ResponseEntity<String> responseEntity = fromBotToDb(city, deleteUrl, HttpMethod.DELETE);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                sendMsg(update.getMessage().getChatId().toString(), "Удалено успешно!");
            } else {
                sendMsg(update.getMessage().getChatId().toString(), "Возникла ошибка при удалении города!");
            }
        } else if (message.startsWith("/start")) {
            sendMsg(update.getMessage().getChatId().toString(), "Основные команды\n" + "all - показать список доступных городов\n" +
                    "add - добавить новый город (/add \"Название города\")\n" +
                    "update - обновить город (/update \"Название города\" \"Описание\")\n" +
                    "delete - удалить город (/delete \"Название города\")");
        } else {
            City city = new City();
            city.setName(message.trim());
            city.setDescription("description");

            String getUrl = "http://localhost:8080/city/find";
            ResponseEntity<String> responseEntity = fromBotToDb(city, getUrl, HttpMethod.POST);
            if (responseEntity.getBody() != null) {
                JSONObject jsonEntity = new JSONObject(responseEntity.getBody());
                String outMessage = jsonEntity.getString("description");
                sendMsg(update.getMessage().getChatId().toString(), outMessage);
            } else {
                sendMsg(update.getMessage().getChatId().toString(), "Город не найден! Вы можете создать его.");
            }
        }
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private ResponseEntity<String> fromBotToDb(City city, String url, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, method, entity, String.class);
    }

    private List<String> getArguments(String message) {
        List<String> arg = new ArrayList<>();

        Pattern pattern = Pattern.compile("(?<=([\"']\\b))(?:(?=(\\\\?))\\2.)*?(?=\\1)");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            arg.add(matcher.group());
        }
        return arg;
    }
}
