package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.Photos;

public interface PhotoRepository extends JpaRepository<Photos,Long> {
    Photos findByPetId(Long petId);
    Photos findByChatId(Long chatId);
}
