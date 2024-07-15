package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "animal_shelters_props")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalShelterProps {
    /**
     * id параметра питомника
     */
    @Id
    @Column(name = "animal_shelters_props_id")
    private Long animalSheltersPropsId;

    /**
     * ФК на справочник параметров
     */
    @Column(name = "prop_id")
    private Long propId;

    /**
     * Значение параметра
     */
    @Column(name = "prop_val")
    private String propVal;

    /**
     * ФК на питомники
     */
    @Column(name = "shelter_id")
    private Long shelterId;

    /**
     * Дата с
     */
    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    /**
     * Дата до (добавил для историзма, но в целом сие не обязательно)
     */
    @Column(name = "date_to")
    private LocalDateTime dateTo;
}
