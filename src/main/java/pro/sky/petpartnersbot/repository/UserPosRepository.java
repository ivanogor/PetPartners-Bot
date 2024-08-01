package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.UserPos;

public interface UserPosRepository  extends JpaRepository<UserPos,Long> {
    UserPos findByChatId(Long chatId);
}
