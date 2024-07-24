package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая свойства приютов для животных. Этот класс отображается на таблицу "animal_shelters_props" в базе данных.
 */
@Entity
@Data
@Table(name = "animal_shelters_props")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalShelterProps {
    @Id
    @Column(name = "animal_shelters_props_id")
    private Long animalSheltersPropsId;

    @Column(name = "prop_id")
    private Long propId;

    @Column(name = "prop_val")
    private String propVal;

    @Column(name = "shelter_id")
    private Long shelterId;

    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;
}