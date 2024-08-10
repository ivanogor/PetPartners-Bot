package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        Long chatId = 123L;
        User user = new User();
        user.setChatId(chatId);
        when(usersRepository.findByChatId(chatId)).thenReturn(user);

        // Act
        User result = userService.findById(chatId);

        // Assert
        assertEquals(user, result);
        verify(usersRepository, times(1)).findByChatId(chatId);
    }
}