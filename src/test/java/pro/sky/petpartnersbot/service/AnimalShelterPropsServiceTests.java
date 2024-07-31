package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;
import pro.sky.petpartnersbot.service.impl.AnimalShelterPropsServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AnimalShelterPropsServiceTests {
    @Mock
    private AnimalShelterPropsRepository animalShelterPropsRepository;
    @InjectMocks
    private AnimalShelterPropsServiceImpl serviceUnderTest;


    @Test
    @DisplayName("Get volunteer chat by id functionality")
    public void givenId_whenGetVolunteerChat_thenStringIsReturned() {
        // given
        long animalShelterId = 1L;
        String returnedString = "test";

        given(animalShelterPropsRepository.getVolunteerChatId(anyLong(), anyLong())).willReturn(returnedString);

        // when
        String obtainedString = serviceUnderTest.getVolunteerChat(animalShelterId);

        // then
        assertThat(obtainedString).isEqualTo(returnedString);
    }}
