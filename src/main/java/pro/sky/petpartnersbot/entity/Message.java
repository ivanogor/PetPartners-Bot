package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
public class Message {
    /**
     * Тип сообщения.
     * Это поле хранит информацию о типе сообщения, которая может использоваться для различных целей,
     * например, для фильтрации или обработки сообщений.
     */
    @Id
    @Column(name = "type")
    private String type;

    /**
     * Текст сообщения.
     * Это поле хранит содержимое сообщения.
     */
    @Column(name = "text")
    private String text;
}