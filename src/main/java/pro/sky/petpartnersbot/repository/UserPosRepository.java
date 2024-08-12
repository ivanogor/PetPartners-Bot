package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.UserPos;

/**
 * Репозиторий для работы с сущностями UserPos.
 * Предоставляет методы для поиска и управления данными о позициях пользователей.
 */
public interface UserPosRepository extends JpaRepository<UserPos, Long> {

    /**
     * Находит информацию о позиции пользователя по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return сущность UserPos
     */
    UserPos findByChatId(Long chatId);
}