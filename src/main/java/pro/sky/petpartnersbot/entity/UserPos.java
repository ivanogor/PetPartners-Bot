package pro.sky.petpartnersbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user_pos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPos {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "pos")
    private String pos;

    @Column(name = "prev_pos")
    private String prevPos;
}
