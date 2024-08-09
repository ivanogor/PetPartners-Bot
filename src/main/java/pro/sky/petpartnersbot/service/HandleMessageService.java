package pro.sky.petpartnersbot.service;

import com.pengrad.telegrambot.model.Update;

/**
 * Интерфейс сервиса для обработки сообщений.
 */
public interface HandleMessageService {
    /**
     * Обрабатывает входящее сообщение от Telegram API.
     * Если пользователь впервые использует чат, добавляет его в базу данных и переходит к анализу сообщений.
     * Иначе перенаправляет пользователя к выбору приюта.
     *
     * @param update Входящее обновление от Telegram API.
     */
    void handleMessage(Update update);
}