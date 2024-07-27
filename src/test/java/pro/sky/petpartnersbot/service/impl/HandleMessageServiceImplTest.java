package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.util.MessageUtils;
import pro.sky.petpartnersbot.util.UserUtils;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

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
    private AnimalShelterPropsServiceImpl animalService;

    @Mock
    private TelegramBot bot;

    @Mock
    private SendResponse response;

    @InjectMocks
    private HandleMessageServiceImpl handleMessageService;

    @Test
    @DisplayName("Test adding new user with /start command functionality")
    public void givenNewUser_whenStartCommand_thenUserAdded() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(null);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("/start");
        given(messageMock.chat().id()).willReturn(chatId);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(userService).should().addUser(any(User.class));
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("util message text")));
    }

    @Test
    @DisplayName("Test check existing user with /start command functionality")
    public void givenExistUser_whenStartCommand_thenUserChooseShelter() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("/start");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Выберите приют")));
    }

    @Test
    @DisplayName("Test for instructions on how to adopt an animal functionality")
    public void givenHowToAdoptAnimal_whenNeedInstructionCommand_thenUserTakeInstructions() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Как взять животное из приюта?");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Тест1")));
    }

    @Test
    @DisplayName("Test for take information about shelter functionality")
    public void givenTakeInformation_whenNeedInformationAboutShelterCommand_thenUserTakeInformation() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Информация о приюте");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Выберите меню:")));
    }

    @Test
    @DisplayName("Test for sending report information functionality")
    public void givenHowToSendReport_whenSendingReportCommand_thenUserTakeInformationAboutReport() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Прислать отчет о питомце");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Тест3")));
    }

    @Test
    @DisplayName("Test for calling volunteer functionality")
    public void givenHowToCallVolunteer_whenNeedVolunteer_thenSendingMessageToVolunteer() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Позвать волонтера");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(animalService.getVolunteerChat(anyLong())).willReturn("1454L");
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Запрос отправлен. С вами свяжутся в течении 10-15 минут")));
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenDeleteCommand_whenNeedDeleteUser_thenDeletingUser() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        Long chatId = 14569L;
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("/delete");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(userService).should().deleteUser(anyLong());
    }
}