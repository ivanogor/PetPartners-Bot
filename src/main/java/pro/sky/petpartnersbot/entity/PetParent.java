package pro.sky.petpartnersbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "pet_parents")
@AllArgsConstructor
@NoArgsConstructor
public class PetParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "is_pets_parent")
    private Boolean isPetsParent;

    @Column(name = "need_report")
    private Boolean needReport;
}
