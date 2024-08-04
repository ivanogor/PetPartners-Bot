package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import pro.sky.petpartnersbot.entity.ShelterRoadMap;


import java.io.IOException;

public interface ShelterRoadMapService {
    void uploadPhoto(TelegramBot bot, Update update);

    void uploadDocument(TelegramBot bot, Update update);

    ShelterRoadMap findRoadMap(Long shelterId);
}