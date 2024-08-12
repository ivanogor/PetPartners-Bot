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
@Table(name = "user_pet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPet {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "time_stamp")
    @Builder.Default
    private LocalDateTime timeStamp = LocalDateTime.now();
}
