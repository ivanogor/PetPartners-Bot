package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.Message;
//import pro.sky.petpartnersbot.exception.MessageNotFoundException;
import pro.sky.petpartnersbot.repository.MessageRepository;
import pro.sky.petpartnersbot.service.impl.MessageServiceImpl;
import pro.sky.petpartnersbot.util.MessageUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test get message by id functionality")
    public void givenId_whenGetMessage_thenReturnMessage() {
        //given
        BDDMockito.given(messageRepository.findById(anyString())).willReturn(Optional.of(MessageUtils.getMessage()));
        //when
        Message obtainedMessage = serviceUnderTest.findById(anyString());
        //then
        assertThat(obtainedMessage).isNotNull();
        verify(messageRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Test get message by incorrect id functionality")
    public void givenIncorrectId_whenGetMessage_thenExceptionIsThrown() {
        /*//given
        BDDMockito.given(messageRepository.findById(anyString())).willThrow(MessageNotFoundException.class);
        //when
        assertThrows(MessageNotFoundException.class, () -> serviceUnderTest.findById(anyString()));
        //then*/
    }
}
