package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.User;

import java.util.List;
/**
 * Репозиторий для работы с пользователями.
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByChatId(Long chatId);
    @Query(value = "select count(1) from users where entity_id = ?1", nativeQuery = true)
    int getCntByEntity(Long entityId);
    List<User> findByEntityId(Long entity_id);
    User findByUserName(String userName);
}
