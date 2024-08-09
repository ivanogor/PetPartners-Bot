package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.Photo;
import pro.sky.petpartnersbot.repository.PhotoRepository;
import pro.sky.petpartnersbot.service.impl.PhotoServiceImpl;
import pro.sky.petpartnersbot.util.PhotoUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {
    @Mock
    private PhotoRepository photoRepository;
    @InjectMocks
    private PhotoServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test get photo by pet id functionality")
    public void givenPetId_whenFindPhotoByPetId_thenPhotoIsReturned() {
        //given
        Photo photo = PhotoUtils.getPhoto();
        given(photoRepository.findByPetId(anyLong())).willReturn(photo);
        //when
        Photo obtainedPhoto = serviceUnderTest.findPhotoByPetId(anyLong());
        //then
        assertThat(obtainedPhoto).isNotNull();
    }
    @Test
    @DisplayName("Test get photo by chat id functionality")
    public void givenChatId_whenFindPhotoByChatId_thenPhotoIsReturned() {
        //given
        Photo photo = new Photo();
        given(photoRepository.findByChatId(anyLong())).willReturn(photo);
        //when
        Photo obtainedPhoto = serviceUnderTest.findPhotoByChatId(anyLong());
        //then
        assertThat(obtainedPhoto).isNotNull();
    }
}
