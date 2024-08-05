package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.PropertyDict;
import pro.sky.petpartnersbot.entity.ShelterRoadMap;

import java.util.List;

public interface ShelterRoadMapRepository extends JpaRepository<ShelterRoadMap, Long> {
    @Query(value = "select srm.road_map_id, srm.adoption_date, srm.image_data " +
            "from shelter_road_map as srm " +
            "inner join animal_shelters_props as asp " +
            "on asp.road_map_id=srm.road_map_id " +
            "where asp.chat_id = ?1 " +
            "and asp.prop_id = 6 " +
            "and asp.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(asp.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    ShelterRoadMap getRoadMap(Long chat_id);
}
