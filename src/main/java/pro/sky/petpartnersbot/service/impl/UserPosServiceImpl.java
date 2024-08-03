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

    /**
     * Находит позицию пользователя по идентификатору чата.
     *
     * @param chatId Идентификатор чата.
     * @return Объект UserPos, соответствующий заданному идентификатору чата.
     */
    public UserPos findByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    /**
     * Сохраняет позицию пользователя.
     *
     * @param userPos Объект UserPos, который нужно сохранить.
     */
    public void saveUserPos(UserPos userPos) {
        repository.save(userPos);
    }
}