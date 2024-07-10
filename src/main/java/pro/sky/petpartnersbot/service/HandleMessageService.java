package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.Update;

public interface HandleMessageService {
    void handleMessage(Update update);
}