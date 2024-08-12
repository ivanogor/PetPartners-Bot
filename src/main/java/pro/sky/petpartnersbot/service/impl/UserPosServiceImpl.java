package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.UserPos;
import pro.sky.petpartnersbot.repository.UserPosRepository;
import pro.sky.petpartnersbot.service.UserPosService;

/**
 * Сервис для работы с позициями пользователей.
 * Этот класс отвечает за поиск и сохранение позиций пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserPosServiceImpl implements UserPosService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserPosRepository repository;

    @Override
    public UserPos findByChatId(Long chatId) {
        logger.info("Was invoked findByChatId method");
        return repository.findByChatId(chatId);
    }

    @Override
    public void saveUserPos(UserPos userPos) {
        logger.info("Was invoked saveUserPos method");
        repository.save(userPos);
    }
}