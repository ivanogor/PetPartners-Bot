package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.User;

import java.util.List;

/**
 * Репозиторий для работы с пользователями.
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    /**
     * Получает пользователя по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return объект User, соответствующий заданному идентификатору чата
     */
    User findByChatId(Long chatId);

    /**
     * Получает количество пользователей по идентификатору сущности.
     *
     * @param entityId идентификатор сущности
     * @return количество пользователей, соответствующих заданному идентификатору сущности
     */
    @Query(value = "select count(1) from users where entity_id = ?1", nativeQuery = true)
    int getCntByEntity(Long entityId);

    /**
     * Получает список пользователей по идентификатору сущности.
     *
     * @param entity_id идентификатор сущности
     * @return список объектов User, соответствующих заданному идентификатору сущности
     */
    List<User> findByEntityId(Long entity_id);

    /**
     * Получает пользователя по имени пользователя.
     *
     * @param userName имя пользователя
     * @return объект User, соответствующий заданному имени пользователя
     */
    User findByUserName(String userName);
}