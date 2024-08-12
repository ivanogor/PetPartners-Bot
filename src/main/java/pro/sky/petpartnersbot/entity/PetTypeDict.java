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
@Table(name = "pet_type_dict")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTypeDict {
    /**
     * id свойства
     */
    @Id
    @Column(name = "pet_type_id")
    private Long petTypeId;
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
}
