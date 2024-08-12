package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.PetTypeDict;

import java.util.List;

/**
 * Репозиторий для работы с типами питомцев.
 * Предоставляет методы для поиска и управления данными о типах питомцев.
 */
public interface PetTypeDictRepository extends JpaRepository<PetTypeDict, Long> {

    /**
     * Возвращает список всех активных типов питомцев.
     *
     * @return список сущностей PetTypeDict
     */
    @Query(value = "select * " +
            "from pet_type_dict ptd " +
            "where ptd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(ptd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    List<PetTypeDict> getAllActivePetTypes();

    /**
     * Возвращает тип питомца по его имени.
     *
     * @param name имя типа питомца
     * @return сущность PetTypeDict
     */
    @Query(value = "select * " +
            "from pet_type_dict ptd " +
            "where ptd.name = ?1 " +
            "and ptd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(ptd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    PetTypeDict getTypeByName(String name);

    /**
     * Находит тип питомца по его идентификатору.
     *
     * @param petTypeId идентификатор типа питомца
     * @return сущность PetTypeDict
     */
    PetTypeDict findByPetTypeId(Long petTypeId);
}