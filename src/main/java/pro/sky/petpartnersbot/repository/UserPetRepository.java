package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.UserPet;

public interface UserPetRepository extends JpaRepository<UserPet, Long> {
    UserPet findByChatId (Long chatId);
}
