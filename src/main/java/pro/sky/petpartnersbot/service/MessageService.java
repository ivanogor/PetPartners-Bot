package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.Message;

/**
 * Интерфейс сервиса для работы с сообщениями.
 */
public interface MessageService {

    /**
     * Находит сообщение по его типу.
     *
     * @param type Тип сообщения.
     * @return Объект Message, соответствующий заданному типу.
     * @throws RuntimeException если сообщение с заданным типом не найдено.
     */
    Message findById(String type);
}