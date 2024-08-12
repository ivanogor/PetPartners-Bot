package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.Message;
import pro.sky.petpartnersbot.repository.MessageRepository;
import pro.sky.petpartnersbot.service.impl.MessageServiceImpl;
import pro.sky.petpartnersbot.util.MessageUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {
    @Mock
    private MessageRepository repository;
    @InjectMocks
    private MessageServiceImpl serviceUnderTests;

    @Test
    @DisplayName("Test find message by type functionality")
    public void givenType_whenFindById_thenMessageIsReturned() {
        //given
        Message message = MessageUtils.getMessage();
        BDDMockito.given(repository.findById(anyString())).willReturn(Optional.of(message));
        //when
        Message obtainedMessage = serviceUnderTests.findById(anyString());
        //then
        assertThat(obtainedMessage).isNotNull();
    }
}
