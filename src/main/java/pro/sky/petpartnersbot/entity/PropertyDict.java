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
 * Сущность, представляющая словарь свойств.
 * Эта сущность используется для хранения информации о различных свойствах в базе данных.
 */
@Entity
@Data
@Table(name = "property_dict")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDict {

    @Id
    @Column(name = "prop_id")
    private Long propId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_from")
    private LocalDateTime dateFrom;


    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "entity_id")
    private Long entityId;
}