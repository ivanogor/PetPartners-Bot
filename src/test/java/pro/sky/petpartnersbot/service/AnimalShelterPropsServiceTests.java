package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;
import pro.sky.petpartnersbot.entity.Pet;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;
import pro.sky.petpartnersbot.service.impl.AnimalShelterPropsServiceImpl;
import pro.sky.petpartnersbot.util.AnimalShelterPropsUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class AnimalShelterPropsServiceTests {
    @Mock
    AnimalShelterPropsRepository repository;

    @InjectMocks
    AnimalShelterPropsServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test get user prop by prop id and animal shelter id functionality")
    public void givenPropIdAndAnimalShelterId_whenGetUserProp_thenAnimalShelterPropsIsReturned() {
        //given
        AnimalShelterProps props = AnimalShelterPropsUtils.getAnimalShelterProps();
        BDDMockito.given(repository.getUserProp(anyLong(), anyLong())).willReturn(props);
        //when
        AnimalShelterProps obtainedProps = serviceUnderTest.getUserProp(anyLong(), anyLong());
        //then
        assertThat(obtainedProps).isNotNull();
    }

    @Test
    @DisplayName("Test get pet prop by prop id and pet id functionality")
    public void givenPropIdAndPetId_whenGetPetProp_thenAnimalShelterPropsIsReturned() {
        //given
        AnimalShelterProps props = AnimalShelterPropsUtils.getAnimalShelterProps();
        BDDMockito.given(repository.getPetProp(anyLong(), anyLong())).willReturn(props);
        //when
        AnimalShelterProps obtainedProps = serviceUnderTest.getPetProp(anyLong(), anyLong());
        //then
        assertThat(obtainedProps).isNotNull();
    }

    @Test
    @DisplayName("Test get user prop by prop id and animal shelter id functionality")
    public void givenAndAnimalShelterProps_whenAddShelterProp_thenAnimalShelterPropsIsReturned() {
        //given
        AnimalShelterProps props = AnimalShelterPropsUtils.getAnimalShelterProps();
        BDDMockito.given(repository.save(any(AnimalShelterProps.class))).willReturn(props);
        //when
        AnimalShelterProps obtainedProps = serviceUnderTest.addShelterProp(props);
        //then
        assertThat(obtainedProps).isNotNull();
    }
}
