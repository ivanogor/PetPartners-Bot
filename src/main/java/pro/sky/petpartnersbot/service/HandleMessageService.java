package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс сервиса для обработки сообщений.
 */
public interface HandleMessageService {
    void handleMessage(Update update);
}