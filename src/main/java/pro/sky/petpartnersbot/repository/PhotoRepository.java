package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.Photo;

/**
 * Репозиторий для работы с фотографиями.
 * Предоставляет методы для поиска и управления данными о фотографиях.
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    /**
     * Находит фотографию по идентификатору питомца.
     *
     * @param petId идентификатор питомца
     * @return сущность Photo
     */
    Photo findByPetId(Long petId);

    /**
     * Находит фотографию по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return сущность Photo
     */
    Photo findByChatId(Long chatId);
}