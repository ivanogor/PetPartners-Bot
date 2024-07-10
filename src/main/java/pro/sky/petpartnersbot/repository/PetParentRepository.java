package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.PetParent;

public interface PetParentRepository extends JpaRepository<PetParent, Long> {
    PetParent findByChatId(Long chatId);
}
