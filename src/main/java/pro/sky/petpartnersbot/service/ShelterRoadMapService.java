package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import pro.sky.petpartnersbot.entity.ShelterRoadMap;
import pro.sky.petpartnersbot.entity.User;


import java.io.IOException;

public interface ShelterRoadMapService {
    Long switchAct(TelegramBot bot, Update update, User user);

    Long uploadPhoto(TelegramBot bot, Update update);

    Long uploadDocument(TelegramBot bot, Update update);

    void getRoadMap(User user);

    ShelterRoadMap findRoadMap(Long shelterId);
}