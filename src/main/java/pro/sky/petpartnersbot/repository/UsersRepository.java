package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.User;

/**
 * Репозиторий для работы с пользователями.
 */
public interface UsersRepository extends JpaRepository<User, Long> {
}
