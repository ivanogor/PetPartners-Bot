package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @Id
    @SequenceGenerator(name = "pets_seq", sequenceName = "pets_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pets_seq")
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "pet_type_id")
    private Long petTypeId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "date_from")
    @Builder.Default
    private LocalDateTime dateFrom = LocalDateTime.now();

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "entity_id")
    private Long entityId;
}
