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
    @SequenceGenerator(name = "animal_shelters_props_seq", sequenceName = "animal_shelters_props_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_shelters_props_seq")
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
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * Дата с
     */
    @Column(name = "date_from")
    @Builder.Default
    private LocalDateTime dateFrom = LocalDateTime.now();

    /**
     * Дата до (добавил для историзма, но в целом сие не обязательно)
     */
    @Column(name = "date_to")
    private LocalDateTime dateTo;
}
