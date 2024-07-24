package pro.sky.petpartnersbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая приют для животных. Этот класс отображается на таблицу "animal_shelters" в базе данных.
 */
@Entity
@Data
@Table(name = "animal_shelters")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalShelter {
    @Id
    @Column(name = "shelter_id")
    private Long shelterId;

    @Column(name = "name")
    private String name;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}