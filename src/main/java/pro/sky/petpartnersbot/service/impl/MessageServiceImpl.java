package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.Message;
import pro.sky.petpartnersbot.repository.MessageRepository;
import pro.sky.petpartnersbot.service.MessageService;

/**
 * Сервис для работы с сообщениями.
 * Этот класс отвечает за поиск сообщений по их типу.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageRepository repository;

    /**
     * Находит сообщение по его типу.
     *
     * @param type Тип сообщения.
     * @return Объект Message, соответствующий заданному типу.
     * @throws RuntimeException если сообщение с заданным типом не найдено.
     */
    @Override
    public Message findById(String type) {
        logger.info("Was invoked find Message by Type method");
        return repository.findById(type).orElseThrow();
    }
}