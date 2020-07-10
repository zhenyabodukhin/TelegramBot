package com.telegram.bot.bot;

import com.telegram.bot.config.bot.BotConfig;
import com.telegram.bot.domain.City;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
            JSONObject returnedCity = fromBotToDb(city, postUrl, HttpMethod.POST);
            String outMessage = returnedCity.getString("name") + " - " + returnedCity.getString("description");

            sendMsg(update.getMessage().getChatId().toString(), outMessage);
        }
        //sendMsg(update.getMessage().getChatId().toString(), message);
    }

    private JSONObject fromBotToDb(City city, String url, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<City> entity = new HttpEntity<>(city, headers);
        RestTemplate restTemplate = new RestTemplate();

        return new JSONObject(Objects.requireNonNull(restTemplate.exchange(url, method, entity, String.class).getBody()));
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
