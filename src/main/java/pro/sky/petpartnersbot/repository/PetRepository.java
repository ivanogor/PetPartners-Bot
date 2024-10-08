package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.Pet;

import java.util.List;

/**
 * Репозиторий для работы с сущностями Pet.
 * Предоставляет методы для поиска и управления данными о питомцах.
 */
public interface PetRepository extends JpaRepository<Pet, Long> {

    /**
     * Возвращает список питомцев по идентификатору чата, которые активны и не привязаны к пользователю.
     *
     * @param chat_id идентификатор чата
     * @return список сущностей Pet
     */
    @Query(value = "SELECT p.pet_id, " +
            "trim(COALESCE(p.name||' ','')||'('||p.pet_id||')') NAME, " +
            "age, pet_type_id, chat_id, date_from, date_to, entity_id " +
            "from pets p " +
            "where p.chat_id = ?1 " +
            "and p.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(p.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP " +
            "and not exists (select 1 from user_pet usp where usp.pet_id=p.pet_id)", nativeQuery = true)
    List<Pet> getAllByChatId(Long chat_id);

    /**
     * Возвращает список питомцев по идентификатору чата, которые активны и привязаны к пользователю.
     *
     * @param chat_id идентификатор чата
     * @return список сущностей Pet
     */
    @Query(value = "SELECT p.pet_id, " +
            "trim(COALESCE(p.name||' ','')||'('||p.pet_id||')')||' '||" +
            "(select u.user_name from users u,user_pet usp where u.chat_id = usp.chat_id and usp.pet_id = p.pet_id) NAME, " +
            "age, pet_type_id, chat_id, date_from, date_to, entity_id " +
            "from pets p " +
            "where p.chat_id = ?1 " +
            "and p.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(p.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP " +
            "and exists (select 1 from user_pet usp where usp.pet_id=p.pet_id)", nativeQuery = true)
    List<Pet> getAllByChatIdWithUser(Long chat_id);

    /**
     * Находит питомца по его идентификатору.
     *
     * @param petId идентификатор питомца
     * @return сущность Pet
     */
    Pet findByPetId(Long petId);
}