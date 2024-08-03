package pro.sky.petpartnersbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая питомца в базе данных.
 * Этот класс отображает таблицу "pet_parents" в базе данных и содержит информацию о питомце,
 * включая его идентификатор чата, имя пользователя, контактные данные владельца и другие атрибуты.
 */
@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "adoption_date")
    private LocalDateTime adoptionDate;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "shl_id")
    private Long shlId;
}