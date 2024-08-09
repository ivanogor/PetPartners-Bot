package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.User;
//import pro.sky.petpartnersbot.exception.UserNotFoundException;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.impl.UserServiceImpl;
import pro.sky.petpartnersbot.util.UserUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test add user functionality")
    public void givenUser_whenAddUser_thenReturnUser() {
        //given
        User userToAdd = UserUtils.getUser();
        BDDMockito.given(usersRepository.save(userToAdd)).willReturn(userToAdd);
        //when
        User savedUser = serviceUnderTest.addUser(userToAdd);
        //then
        assertThat(savedUser).isNotNull();
    }
    @Test
    @DisplayName("Test find by id functionality")
    public void givenId_whenFindById_thenReturnUser() {
        //given
        User user = UserUtils.getUser();
        BDDMockito.given(usersRepository.findById(anyLong())).willReturn(Optional.of(user));
        //when
        User obtainedUser = serviceUnderTest.findById(anyLong());
        //then
        assertThat(obtainedUser).isNotNull();
        assertThat(obtainedUser.getChatId()).isNotNull();
    }
    @Test
    @DisplayName("Test find by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
       /* //given
        User user = UserUtils.getUser();
        BDDMockito.given(usersRepository.findById(anyLong())).willThrow(UserNotFoundException.class);
        //when
        assertThrows(UserNotFoundException.class, () -> serviceUnderTest.findById(anyLong()));
        //then
        verify(usersRepository, times(1)).findById(anyLong());*/
    }

    @Test
    @DisplayName("Test delete by id functionality")
    public void givenId_whenDeleteById_thenRepositoryDeleteMethodIsCalled() {
        //given
        BDDMockito.given(usersRepository.findById(anyLong())).willReturn(Optional.of(UserUtils.getUser()));
        //when
        serviceUnderTest.deleteUser(anyLong());
        //then
        verify(usersRepository, times(1)).findById(anyLong());
        verify(usersRepository, times(1)).deleteById(anyLong());
    }
    @Test
    @DisplayName("Test delete by id functionality")
    public void givenIncorrectId_whenDeleteById_thenExceptionIsThrown() {
       /* //given
        BDDMockito.given(usersRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> serviceUnderTest.deleteUser(anyLong()));
        //then
        verify(usersRepository, never()).deleteById(anyLong());*/
    }
}
