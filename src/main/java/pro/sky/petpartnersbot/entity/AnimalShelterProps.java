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
    @SequenceGenerator(name = "animal_shelters_props_seq", sequenceName = "animal_shelters_props_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_shelters_props_seq")
    @Column(name = "animal_shelters_props_id")
    private Long animalSheltersPropsId;

    @Column(name = "prop_id")
    private Long propId;

    @Column(name = "prop_val")
    private String propVal;

    /**
     * ФК на питомники
     */
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "date_from")
    @Builder.Default
    private LocalDateTime dateFrom = LocalDateTime.now();

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "road_map_id")
    private Long roadMapId;
}