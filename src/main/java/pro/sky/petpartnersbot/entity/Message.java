package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая сообщение в базе данных.
 * Этот класс отображает таблицу "messages" в базе данных и содержит информацию о тексте и типе сообщения.
 */
@Data
@Entity
@Table(name = "messages")
@NoArgsConstructor
public class Message {
    /**
     * Уникальный идентификатор сообщения.
     * Это поле генерируется автоматически и является первичным ключом в таблице.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Текст сообщения.
     * Это поле хранит содержимое сообщения.
     */
    @Column(name = "text")
    private String text;

    /**
     * Тип сообщения.
     * Это поле хранит информацию о типе сообщения, которая может использоваться для различных целей,
     * например, для фильтрации или обработки сообщений.
     */
    @Column(name = "type")
    private String type;
}