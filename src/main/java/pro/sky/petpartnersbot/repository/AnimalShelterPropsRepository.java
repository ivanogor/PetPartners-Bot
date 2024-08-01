package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;

public interface AnimalShelterPropsRepository extends JpaRepository<AnimalShelterProps, String> {
    @Query(value = "select * " +
                   "from animal_shelters_props asp " +
                   "where asp.prop_id = ?1 " +
                   "and asp.chat_id = ?2 " +
                   "and asp.date_from<=CURRENT_TIMESTAMP " +
                   "and COALESCE(asp.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    AnimalShelterProps getUserProp(Long prop_id,Long shelter_id);

}
