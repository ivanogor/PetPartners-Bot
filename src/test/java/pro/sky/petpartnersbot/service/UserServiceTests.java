package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.User;
import pro.sky.petpartnersbot.repository.UsersRepository;
import pro.sky.petpartnersbot.service.impl.UserServiceImpl;
import pro.sky.petpartnersbot.util.UserUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test add user functionality")
    public void givenUser_whenAddUser_thenUserIsReturn() {
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
    public void givenId_whenFindById_thenUserIsReturn() {
        //given
        User user = UserUtils.getUser();
        BDDMockito.given(usersRepository.findByChatId(anyLong())).willReturn(user);
        //when
        User obtainedUser = serviceUnderTest.findById(anyLong());
        //then
        assertThat(obtainedUser).isNotNull();
        assertThat(obtainedUser.getChatId()).isNotNull();
    }

    @Test
    @DisplayName("Test delete by id functionality")
    public void givenId_whenDeleteById_thenRepositoryDeleteMethodIsCalled() {
        //given;
        //when
        serviceUnderTest.deleteUser(anyLong());
        //then
        verify(usersRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test find all users by entity id functionality")
    public void givenId_whenFindAllUsersByEntityId_thenListOfObtainedUserIsReturned() {
        //given
        List<User> users = UserUtils.getUsers();
        BDDMockito.given(usersRepository.findByEntityId(anyLong())).willReturn(users);
        //when
        List<User> obtainedUsers = serviceUnderTest.findAllUsersByEntityId(anyLong());
        //then
        assertThat(obtainedUsers).isNotNull();
        assertThat(obtainedUsers.size()).isEqualTo(users.size());
        assertThat(obtainedUsers).containsAll(users);
    }

    @Test
    @DisplayName("Test find user by name functionality")
    public void givenUserName_whenFindByUserName_thenUserIsReturned() {
        //given
        User user = UserUtils.getUser();
        BDDMockito.given(usersRepository.findByUserName(anyString())).willReturn(user);
        //when
        User obtaineUser = serviceUnderTest.findByUserName(anyString());
        //then
        assertThat(obtaineUser).isNotNull();
        assertThat(obtaineUser).isEqualTo(user);
    }
}
