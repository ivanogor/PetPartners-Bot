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
    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "text")
    private String text;
}