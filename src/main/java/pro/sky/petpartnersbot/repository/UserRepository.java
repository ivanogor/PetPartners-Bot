package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(Long chatId);
}