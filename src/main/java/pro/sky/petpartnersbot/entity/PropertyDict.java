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
@Table(name = "property_dict")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDict {
    /**
     * id свойства
     */
    @Id
    @Column(name = "prop_id")
    private Long propId;

    /**
     * имя свойства
     */
    @Column(name = "name")
    private String name;

    /**
     * с какой даты свойство активно
     */
    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    /**
     * когда свойство стало не активно
     */
    @Column(name = "date_to")
    private LocalDateTime dateTo;

    /**
     * фк на справочник сущностей
     */
    @Column(name = "entity_id")
    private Long entityId;
}
