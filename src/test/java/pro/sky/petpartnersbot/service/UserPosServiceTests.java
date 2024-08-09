package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.UserPos;
import pro.sky.petpartnersbot.repository.UserPosRepository;
import pro.sky.petpartnersbot.service.impl.UserPosServiceImpl;
import pro.sky.petpartnersbot.util.UserPosUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserPosServiceTests {
    @Mock
    private UserPosRepository usersPosRepository;

    @InjectMocks
    private UserPosServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test find userPos by chat id functionality")
    public void givenChatId_whenFindByChatId_thenUserPosIsReturned() {
        //given
        UserPos userPos = UserPosUtils.getUserPos();
        BDDMockito.given(usersPosRepository.findByChatId(anyLong())).willReturn(userPos);
        //when
        UserPos obtainedUserPos = serviceUnderTest.findByChatId(anyLong());
        //then
        assertThat(obtainedUserPos).isNotNull();
        assertThat(obtainedUserPos.getChatId()).isEqualTo(userPos.getChatId());
    }

    @Test
    @DisplayName("Test save userPos functionality")
    public void givenUserPos_whenSaveUserPos_thenMethodIsCalled() {
        //given
        UserPos userPosToSave = UserPosUtils.getUserPos();
        BDDMockito.given(usersPosRepository.save(any(UserPos.class))).willReturn(userPosToSave);
        //when
        serviceUnderTest.saveUserPos(userPosToSave);
        //then
        verify(usersPosRepository).save(any(UserPos.class));
        verify(usersPosRepository, times(1)).save(any(UserPos.class));
    }
}
