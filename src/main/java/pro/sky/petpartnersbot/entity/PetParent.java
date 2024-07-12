package pro.sky.petpartnersbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Сущность, представляющая питомца в базе данных.
 * Этот класс отображает таблицу "pet_parents" в базе данных и содержит информацию о питомце,
 * включая его идентификатор чата, имя пользователя, контактные данные владельца и другие атрибуты.
 */
@Entity
@Data
@Table(name = "pet_parents")
@AllArgsConstructor
@NoArgsConstructor
public class PetParent {
    /**
     * Уникальный идентификатор питомца.
     * Это поле генерируется автоматически и является первичным ключом в таблице.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Идентификатор чата Telegram, связанный с владельцем питомца.
     */
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * Имя пользователя (username) владельца питомца в Telegram.
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * Имя владельца питомца.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия владельца питомца.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Контактные данные владельца питомца.
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * Флаг, указывающий, является ли пользователь владельцем питомца.
     */
    @Column(name = "is_pets_parent")
    private Boolean isPetsParent;

    /**
     * Флаг, указывающий, нужно ли отправлять отчеты этому пользователю.
     */
    @Column(name = "need_report")
    private Boolean needReport;
}