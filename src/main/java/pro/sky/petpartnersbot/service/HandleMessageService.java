package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс сервиса для обработки сообщений.
 */
public interface HandleMessageService {
    /**
     * Обрабатывает входящее сообщение.
     *
     * @param update объект, содержащий информацию о входящем сообщении
     */
    void handleMessage(Update update);
}