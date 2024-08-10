package pro.sky.petpartnersbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "photos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @Id
    @SequenceGenerator(name = "photo_seq", sequenceName = "photo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    @Column(name = "photo_id")
    Long photoId;

    @Lob
    @Column(name = "data")
    byte[] data;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "file_size")
    long fileSize;

    @Column(name = "media_type")
    String mediaType;

    @Column(name = "pet_id")
    Long petId;

    @Column(name = "chat_id")
    Long chatId;
}
