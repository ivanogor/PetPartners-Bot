package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.UserPet;

/**
 * Репозиторий для работы с сущностями UserPet.
 * Предоставляет методы для поиска и управления данными о питомцах пользователей.
 */
public interface UserPetRepository extends JpaRepository<UserPet, Long> {

    /**
     * Находит информацию о питомце пользователя по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return сущность UserPet
     */
    UserPet findByChatId(Long chatId);

    /**
     * Находит информацию о питомце пользователя по идентификатору питомца.
     *
     * @param petId идентификатор питомца
     * @return сущность UserPet
     */
    UserPet findByPetId(Long petId);
}