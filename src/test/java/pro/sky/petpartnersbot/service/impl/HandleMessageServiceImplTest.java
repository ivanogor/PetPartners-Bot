package pro.sky.petpartnersbot.service.impl;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
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
import pro.sky.petpartnersbot.entity.*;
import pro.sky.petpartnersbot.service.utils.KeyboardsForAnswer;
import pro.sky.petpartnersbot.util.*;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    private PropertyDictServiceImpl propertyDictService;

    @Mock
    private PetTypeDictSeviceImpl petTypeDictSevice;

    @Mock
    private PetServiceImpl petService;

    @Mock
    private UserPosServiceImpl userPosService;

    @Mock
    private PhotoServiceImpl photoService;

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
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("text")));
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.START_KEYBOARD))));
    }

    @Test
    @DisplayName("Test check existing user with /start command and shelter entity functionality")
    public void givenExistUser_whenStartCommandAndShelterEntity_thenUserChooseShelter() {
        //given
        User obtainUser = UserUtils.getUser();
        obtainUser.setEntityId(1L);
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
                .get("text").equals("Выберите дальнейшее действие")));
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.PETS_KEYBOARD))));
    }

    @Test
    @DisplayName("Test check existing user with /start command and user entity functionality")
    public void givenExistUser_whenStartCommandAndUserEntity_thenUserChooseShelter() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        obtainUser.setEntityId(2L);
        List<User> users = new ArrayList<>();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("/start");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        given(userService.getAllByEntId(anyLong())).willReturn(users);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters().get("text")
                .equals("text")));
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.NO_INFO_KEYBOARD))));
    }

    @Test
    @DisplayName("Test check existing user with /start command and user entity with shelter functionality")
    public void givenExistUser_whenStartCommandAndUserEntityWithShelter_thenUserChooseShelter() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        obtainUser.setEntityId(2L);
        obtainUser.setShlId(1L);
        List<PropertyDict> propertyDicts = new ArrayList<>();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("/start");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        given(propertyDictService.findFilledByChatIdAndEntityId(anyLong(), anyLong())).willReturn(propertyDicts);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters().get("text")
                .equals("text")));
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.NO_INFO_KEYBOARD_CLNT))));
    }

    @Test
    @DisplayName("Test for instructions on how to adopt an animal functionality")
    public void givenHowToAdoptAnimal_whenNeedInstructionCommand_thenUserTakeInstructions() {
        //given
        User obtainUser = UserUtils.getUser();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Приют");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Выберите дальнейшее действие")));
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.PETS_KEYBOARD))));

    }

    @Test
    @DisplayName("Test for take information about shelter functionality")
    public void givenTakeInformation_whenNeedInformationAboutShelterCommand_thenUserTakeInformation() {
        //given
        User obtainUser = UserUtils.getUser();
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
                .get("text").equals("Действующих разделов о приюте не найдено свяжитесь с администратором бота")));
    }

    @Test
    @DisplayName("Test for client entity functionality")
    public void givenClientMenu_whenClientCommand_thenUserTakeClientMenu() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Клиент");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.NO_INFO_KEYBOARD))));
    }

    @Test
    @DisplayName("Test check adding pets functionality")
    public void givenPet_whenAddingPet_thenBuildPet() {
        //given
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        obtainUser.setEntityId(1L);
        obtainUser.setShlId(1L);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Добавить питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters().get("text")
                .equals("text")));
        verify(petService, times(1)).addPet(any(Pet.class));
    }

    @Test
    @DisplayName("Test check get all pets functionality")
    public void givenPets_whenResponseAllPets_thenGetAllPets() {
        //given
        ReplyKeyboardMarkup propsKeyboard = new ReplyKeyboardMarkup("").resizeKeyboard(true)
                .oneTimeKeyboard(false);
        propsKeyboard.addRow(new KeyboardButton("Добавить питомца"));
        propsKeyboard.addRow(new KeyboardButton("Назад"));
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        User obtainUser = UserUtils.getUser();
        obtainUser.setEntityId(1L);
        obtainUser.setShlId(1L);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Все питомцы приюта");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters().get("text")
                .equals("text")));
        verify(petService, times(1)).getAllShltPets(anyLong());
    }

    @Test
    @DisplayName("Test for calling volunteer functionality")
    public void givenHowToCallVolunteer_whenNeedVolunteer_thenSendingMessageToVolunteer() {
        //given
        User obtainUser = UserUtils.getUser();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Позвать волонтера");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Приют не указал чат для волонтеров")));
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenDeleteCommand_whenNeedDeleteUser_thenDeletingUser() {
        //given
        User obtainUser = UserUtils.getUser();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Удалить учетную запись");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Учетная запись удалена")));
        then(userService).should().deleteUser(anyLong());
    }

    @Test
    @DisplayName("Test delete pet functionality")
    public void givenDeletePetCommand_whenNeedDeletePet_thenDeletingPet() {
        //given
        User obtainUser = UserUtils.getUser();
        Pet obtainPet = PetUtils.getPet();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Удалить питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(petService.findPetBypetId(anyLong())).willReturn(obtainPet);
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Данная функция не поддерживается")));
    }

    @Test
    @DisplayName("Test another shelter switch functionality")
    public void givenSwitchShelterCommand_whenNeedSwitchShelter_thenSwitching() {
        //given
        User obtainUser = UserUtils.getUser();
        pro.sky.petpartnersbot.entity.Message obtainMessage = MessageUtils.getMessage();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Выбрать другой приют");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(messageService.findById(anyString())).willReturn(obtainMessage);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat((sendMessage -> sendMessage.getParameters().get("reply_markup")
                .equals(KeyboardsForAnswer.NO_INFO_KEYBOARD))));
    }

    @Test
    @DisplayName("Test phone number check functionality")
    public void givenPhoneNumber_whenNeedCheckIt_thenChecking() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Мой номер телефона");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Введите номер телефона в формате +7-9**-***-**-**\nТекущий номер: ")));
    }

    @Test
    @DisplayName("Test enter name of pet functionality")
    public void givenPetName_whenInputIt_thenProcessing() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        Pet obtainPet = PetUtils.getPet();
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Имя питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(petService.findPetBypetId(anyLong())).willReturn(obtainPet);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Информация отсутсвует")));
    }

    @Test
    @DisplayName("Test enter age of pet functionality")
    public void givenPetAge_whenInputIt_thenProcessing() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        Pet obtainPet = PetUtils.getPet();
        obtainUser.setEntityId(1L);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Возраст питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(petService.findPetBypetId(anyLong())).willReturn(obtainPet);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Вы можете ввести возраст питомца\nТекущий возраст: ")));
    }

    @Test
    @DisplayName("Test post road map functionality")
    public void givenRoadMap_whenNeedPostIt_thenHandling() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        Photos obtainPhoto = PhotoUtils.getPhoto();
        obtainUser.setEntityId(1L);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Схема проезда");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(photoService.findPhotoByChatId(anyLong())).willReturn(obtainPhoto);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendDocument -> sendDocument.getParameters()
                .get("caption").equals("Вы можете добавить/обновить схему проезда\n")));
    }

    @Test
    @DisplayName("Test post pet photo functionality")
    public void givenPetPhoto_whenNeedPostIt_thenHandling() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        Photos obtainPhoto = PhotoUtils.getPhoto();
        obtainUser.setEntityId(1L);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Фото питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(photoService.findPhotoByPetId(anyLong())).willReturn(obtainPhoto);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendDocument -> sendDocument.getParameters()
                .get("caption").equals("Вы можете добавить/обновить фото питомца\n")));
    }

    @Test
    @DisplayName("Test set pet type functionality")
    public void givenPetType_whenNeedSetIt_thenProcessing() {
        //given
        User obtainUser = UserUtils.getUser();
        UserPos obtainUserPos = UserPosUtils.getUserPos();
        Photos obtainPhoto = PhotoUtils.getPhoto();
        PetTypeDict obtainPetTypeDict = PetTypeDictUtils.getPetTypeDict();
        Pet obtainPet = PetUtils.getPet();
        obtainUser.setEntityId(1L);
        List<PetTypeDict> petTypeDictLst = new ArrayList<>();
        petTypeDictLst.add(obtainPetTypeDict);
        given(userService.findById(anyLong())).willReturn(obtainUser);
        given(updateMock.message()).willReturn(messageMock);
        given(messageMock.chat()).willReturn(chatMock);
        given(messageMock.text()).willReturn("Тип питомца");
        given(messageMock.chat().id()).willReturn(obtainUser.getChatId());
        given(userPosService.findByChatId(anyLong())).willReturn(obtainUserPos);
        given(petTypeDictSevice.getAllActiveTypes()).willReturn(petTypeDictLst);
        given(petTypeDictSevice.getTypeById(anyLong())).willReturn(obtainPetTypeDict);
        given(petService.findPetBypetId(anyLong())).willReturn(obtainPet);
        given(bot.execute(any(SendMessage.class))).willReturn(response);
        given(response.isOk()).willReturn(true);
        //when
        handleMessageService.handleMessage(updateMock);
        //then
        then(bot).should().execute(argThat(sendMessage -> sendMessage.getParameters()
                .get("text").equals("Выберите тип питомца\nТекущий тип: name")));
    }

   /* @Test
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
    }*/
}