package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.PropertyDict;

import java.util.List;

public interface PropertyDictRepository extends JpaRepository<PropertyDict,Long> {
    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.entity_id = ?1 " +
            "and pd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    List<PropertyDict> getByEntityId(Long id);

    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.name = ?1 " +
            "and pd.entity_id= ?2 " +
            "and pd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    PropertyDict getByNameAndEntityId(String name,Long id);

    @Query(value = "select * " +
            "from property_dict pd " +
            "where pd.entity_id = ?1 " +
            "and pd.prop_id<>5 " +
            "and pd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(pd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP " +
            "and exists(select 1 " +
            "from animal_shelters_props asp " +
            "where asp.chat_id = ?2 " +
            "and asp.prop_id = pd.prop_id " +
            "and asp.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(asp.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP)", nativeQuery = true)
    List<PropertyDict> getChatIdFilled(Long entity_id,Long chat_id);
}