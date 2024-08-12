package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.UserPos;

public interface UserPosService {

    /**
     * Находит позицию пользователя по идентификатору чата.
     *
     * @param chatId Идентификатор чата.
     * @return Объект UserPos, соответствующий заданному идентификатору чата.
     */
    UserPos findByChatId(Long chatId);

    /**
     * Сохраняет позицию пользователя.
     *
     * @param userPos Объект UserPos, который нужно сохранить.
     */
    void saveUserPos(UserPos userPos);
}
