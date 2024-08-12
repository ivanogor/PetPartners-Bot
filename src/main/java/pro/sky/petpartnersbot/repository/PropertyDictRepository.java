package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.PropertyDict;

import java.util.List;

/**
 * Репозиторий для работы со свойствами словаря.
 */
public interface PropertyDictRepository extends JpaRepository<PropertyDict, Long> {

    /**
     * Получает список свойств по идентификатору сущности.
     *
     * @param id идентификатор сущности
     * @return список объектов PropertyDict, соответствующих заданному идентификатору сущности
     */
    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.entity_id = ?1 " +
            "and pd.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP", nativeQuery = true)
    List<PropertyDict> getByEntityId(Long id);

    /**
     * Получает свойство по имени и идентификатору сущности.
     *
     * @param name имя свойства
     * @param id идентификатор сущности
     * @return объект PropertyDict, соответствующий заданным имени и идентификатору сущности
     */
    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.name = ?1 " +
            "and pd.entity_id = ?2 " +
            "and pd.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP", nativeQuery = true)
    PropertyDict getByNameAndEntityId(String name, Long id);

    /**
     * Получает список свойств по идентификатору сущности и идентификатору чата, исключая свойства с определенным prop_id.
     *
     * @param entityId идентификатор сущности
     * @param chatId идентификатор чата
     * @return список объектов PropertyDict, соответствующих заданным идентификаторам сущности и чата
     */
    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.entity_id = ?1 " +
            "and pd.prop_id <> 5 " +
            "and pd.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP " +
            "and exists(select 1 " +
            "from animal_shelters_props asp " +
            "where asp.chat_id = ?2 " +
            "and asp.prop_id = pd.prop_id " +
            "and asp.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(asp.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP)", nativeQuery = true)
    List<PropertyDict> getChatIdFilled(Long entityId, Long chatId);

    /**
     * Получает список свойств по идентификатору сущности и идентификатору питомца, исключая свойства с определенным prop_id.
     *
     * @param entityId идентификатор сущности
     * @param petId идентификатор питомца
     * @return список объектов PropertyDict, соответствующих заданным идентификаторам сущности и питомца
     */
    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.entity_id = ?1 " +
            "and pd.prop_id <> 5 " +
            "and pd.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP " +
            "and exists(select 1 " +
            "from animal_shelters_props asp " +
            "where asp.pet_id = ?2 " +
            "and asp.prop_id = pd.prop_id " +
            "and asp.date_from <= CURRENT_TIMESTAMP " +
            "and COALESCE(asp.date_to, CURRENT_DATE + 1) > CURRENT_TIMESTAMP)", nativeQuery = true)
    List<PropertyDict> getFilledByEntityIdAndPetId(Long entityId, Long petId);
}