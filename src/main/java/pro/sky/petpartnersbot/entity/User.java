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
    /**
     * Идентификатор чата Telegram, связанный с владельцем питомца.
     */
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * Имя пользователя (username) владельца питомца в Telegram.
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * Контактные данные владельца питомца.
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * Флаг, указывающий, является ли пользователь владельцем питомца.
     */
    @Column(name = "adoption_date")
    private LocalDateTime adoptionDate;
}