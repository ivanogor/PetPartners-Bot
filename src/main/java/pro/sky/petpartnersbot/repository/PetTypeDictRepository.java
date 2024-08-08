package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.petpartnersbot.entity.PetTypeDict;

import java.util.List;

public interface PetTypeDictRepository extends JpaRepository<PetTypeDict,Long> {
    @Query(value = "select * " +
            "from pet_type_dict ptd " +
            "where ptd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(ptd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    List<PetTypeDict> getAllActivePetTypes();

    @Query(value = "select * " +
            "from pet_type_dict ptd " +
            "where ptd.name = ?1 " +
            "and ptd.date_from<=CURRENT_TIMESTAMP " +
            "and COALESCE(ptd.date_to,CURRENT_DATE+1)>CURRENT_TIMESTAMP", nativeQuery = true)
    PetTypeDict getTypeByName(String name);

    PetTypeDict findByPetTypeId(Long petTypeId);
}
