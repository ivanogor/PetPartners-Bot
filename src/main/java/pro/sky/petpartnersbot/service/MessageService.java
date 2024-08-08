package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Message;

/**
 * Интерфейс сервиса для работы с сообщениями.
 */
public interface MessageService {
    Message findById(String type);
}