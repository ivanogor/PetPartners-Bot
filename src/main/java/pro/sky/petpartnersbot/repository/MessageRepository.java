package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.Message;

public interface MessageRepository extends JpaRepository<Message, String> {
}
