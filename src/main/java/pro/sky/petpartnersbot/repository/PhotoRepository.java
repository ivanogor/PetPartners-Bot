package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    Photo findByPetId(Long petId);
    Photo findByChatId(Long chatId);
}
