package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.util.MessageUtils;
import pro.sky.petpartnersbot.util.UserUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandleMessageServiceImplTest {

    @Mock
    private Update updateMock;

    @Mock
    private Message messageMock;

    @Mock
    private Chat chatMock;

    @Mock
    private pro.sky.petpartnersbot.entity.Message message;

    @Mock
    private MessageServiceImpl messageService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TelegramBot bot;

    @Mock
    private SendResponse response;

    @InjectMocks
    private HandleMessageServiceImpl handleMessageService;

    /*@Test
    @DisplayName("Test get message text, chat Id and invoke processText() method functionality")
    public void givenEmptyUpdate_whenHandleMessage_thenReturnNoInvokeProcessTextMethod() {
        //given
        Update update = new Update();
        //when
        handleMessageService.handleMessage(update);
        //then
        verify(handleMessageService, times(0)).processText("111", 111L, new Update());
    }*/

    @Test
    @DisplayName("Test adding new user with /start command functionality")
    public void givenNewUser_whenStartCommand_thenUserAdded() {
        //given
        String token = "7265930543:AAF4mHcBObiZwj255VLrmLMpf70WLg3nVMw";
        bot = new TelegramBot(token);
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        BDDMockito.given(userService.findById(anyLong())).willReturn(null);
        BDDMockito.given(updateMock.message()).willReturn(messageMock);
        BDDMockito.given(messageMock.chat()).willReturn(chatMock);
        BDDMockito.given(messageService.findById(anyString())).willReturn(obtainMessage);
        //when
        handleMessageService.processText("/start", 111L, updateMock);
        //then
        verify(userService, times(1)).addUser(any());
    }

    @Test
    @DisplayName("Test delete user with /delete command functionality")
    public void givenUser_whenDeleteCommand_thenUserDeleted() {
        //given
        String token = "7265930543:AAF4mHcBObiZwj255VLrmLMpf70WLg3nVMw";
        bot = new TelegramBot(token);
        User obtainUser = UserUtils.getUser();
        //when
        handleMessageService.processText("/delete", 111L, updateMock);
        //then
        verify(userService, times(1)).deleteUser(anyLong());
    }
}