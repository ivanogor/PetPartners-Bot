package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.Pet;
import pro.sky.petpartnersbot.repository.PetRepository;
import pro.sky.petpartnersbot.service.impl.PetServiceImpl;
import pro.sky.petpartnersbot.util.PetUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class PetServiceTests {
    @Mock
    private PetRepository petRepository;
    @InjectMocks
    private PetServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test get all shlt pets functionality")
    public void givenChatId_whenGetAllShltPets_thenListOfPetsReturned() {
        //given
        List<Pet> pets = PetUtils.getPets();
        BDDMockito.given(petRepository.getAllByChatId(anyLong())).willReturn(pets);
        //when
        List<Pet> obtainedPets = serviceUnderTest.getAllShltPets(anyLong());
        //then
        assertThat(obtainedPets).isNotNull();
        assertThat(obtainedPets.size()).isEqualTo(pets.size());
    }

    @Test
    @DisplayName("Test add pet functionality")
    public void givenPet_whenAddPet_thenPetIsReturned() {
        //given
        Pet pet = PetUtils.getPet();
        BDDMockito.given(petRepository.save(any(Pet.class))).willReturn(pet);
        //when
        Pet obtainedPet = serviceUnderTest.addPet(pet);
        //then
        assertThat(obtainedPet).isNotNull();
    }

    @Test
    @DisplayName("Test find pet by pet id functionality")
    public void givenPetId_whenFindPetByPetId_thenPetIsReturned() {
        //given
        Pet pet = PetUtils.getPet();
        BDDMockito.given(petRepository.findByPetId(anyLong())).willReturn(pet);
        //when
        Pet obtainedPet = serviceUnderTest.findPetByPetId(anyLong());
        //then
        assertThat(obtainedPet).isNotNull();
    }
}
