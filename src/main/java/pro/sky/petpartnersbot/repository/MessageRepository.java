package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.Message;

/**
 * Репозиторий для работы с сообщениями.
 */
public interface MessageRepository extends JpaRepository<Message, String> {
}