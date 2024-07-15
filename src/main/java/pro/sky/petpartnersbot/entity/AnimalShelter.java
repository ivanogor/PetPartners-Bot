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

@Entity
@Data
@Table(name = "animal_shelters")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalShelter {
    /**
     * id Питомника
     */
    @Id
    @Column(name = "shelter_id")
    private Long shelterId;

    /**
     * Название питомника
     */
    @Column(name = "name")
    private String name;

    /**
     * Дата добавления записи
     */
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
