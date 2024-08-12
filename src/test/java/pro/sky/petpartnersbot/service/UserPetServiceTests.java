package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.UserPet;
import pro.sky.petpartnersbot.repository.UserPetRepository;
import pro.sky.petpartnersbot.service.impl.UserPetServiceImpl;
import pro.sky.petpartnersbot.util.UserPetUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserPetServiceTests {
    @Mock
    private UserPetRepository userPetRepository;
    @InjectMocks
    private UserPetServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test save user pet functionality")
    public void givenUserPet_whenSaveUserPet_thenMethodIsCalled() {
        //given
        UserPet userPet = UserPetUtils.getUserPet();
        //when
        serviceUnderTest.saveUserPet(userPet);
        //then
        verify(userPetRepository, times(1)).save(userPet);
    }

    @Test
    @DisplayName("Test delete user pet functionality")
    public void givenUserPet_whenDeleteUserPet_thenMethodIsCalled() {
        //given
        UserPet userPet = UserPetUtils.getUserPet();
        //when
        serviceUnderTest.deleteUserPet(userPet);
        //then
        verify(userPetRepository, times(1)).delete(userPet);
    }

    @Test
    @DisplayName("Test get user pet by chat id functionality")
    public void givenChatId_whenGetUserPet_thenUserPetIsReturned() {
        //given
        UserPet userPet = UserPetUtils.getUserPet();
        BDDMockito.given(userPetRepository.findByChatId(anyLong())).willReturn(userPet);
        //when
        UserPet obtainedUserPet = serviceUnderTest.getUserPet(anyLong());
        //then
        assertThat(obtainedUserPet).isNotNull();
    }

    @Test
    @DisplayName("Test get user pet by pet id functionality")
    public void givenPetId_whenGetUserPetByPetId_thenUserPetIsReturned() {
        //given
        UserPet userPet = UserPetUtils.getUserPet();
        BDDMockito.given(userPetRepository.findByPetId(anyLong())).willReturn(userPet);
        //when
        UserPet obtainedUserPet = serviceUnderTest.getUserPetByPetId(anyLong());
        //then
        assertThat(obtainedUserPet).isNotNull();
    }
}
