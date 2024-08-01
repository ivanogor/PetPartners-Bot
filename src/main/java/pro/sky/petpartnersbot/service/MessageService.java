package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Message;

/**
 * Интерфейс сервиса для работы с сообщениями.
 */
public interface MessageService {
    /**
     * Находит сообщение по его идентификатору.
     *
     * @param type идентификатор сообщения
     * @return найденное сообщение
     */
    Message findById(String type);
}