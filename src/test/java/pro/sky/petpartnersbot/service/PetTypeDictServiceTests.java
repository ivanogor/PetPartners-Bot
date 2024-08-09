package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.PetTypeDict;
import pro.sky.petpartnersbot.repository.PetTypeDictRepository;
import pro.sky.petpartnersbot.service.impl.PetTypeDictServiceImpl;
import pro.sky.petpartnersbot.util.PetTypeDictUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PetTypeDictServiceTests {
    @Mock
    private PetTypeDictRepository petTypeDictRepository;

    @InjectMocks
    private PetTypeDictServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test get pet type dict functionality")
    public void given_whenGetAllActiveTypes_thenListOfPetTypeDictIsReturned() {
        //given
        List<PetTypeDict> petTypeDicts = PetTypeDictUtils.getPetTypeDictList();
        BDDMockito.given(petTypeDictRepository.getAllActivePetTypes()).willReturn(petTypeDicts);
        //when
        List<PetTypeDict> obtainedTypeDicts = serviceUnderTest.getAllActiveTypes();
        //then
        assertThat(obtainedTypeDicts).isNotNull();
        assertThat(obtainedTypeDicts.size()).isEqualTo(petTypeDicts.size());
    }

    @Test
    @DisplayName("Test get pet type dict by pet type id functionality")
    public void givenPetTypeId_whenGetTypeById_thenPetTypeDictIsReturned() {
        //given
        PetTypeDict petTypeDict = PetTypeDictUtils.getPetTypeDict();
        BDDMockito.given(petTypeDictRepository.findByPetTypeId(anyLong())).willReturn(petTypeDict);
        //when
        PetTypeDict obtainedTypeDict = serviceUnderTest.getTypeById(anyLong());
        //then
        assertThat(obtainedTypeDict).isNotNull();
    }

    @Test
    @DisplayName("Test get pet type dict by name functionality")
    public void givenName_whenGetTypeByName_thenPetTypeDictIsReturned() {
        //given
        PetTypeDict petTypeDict = PetTypeDictUtils.getPetTypeDict();
        BDDMockito.given(petTypeDictRepository.getTypeByName(anyString())).willReturn(petTypeDict);
        //when
        PetTypeDict obtainedTypeDict = serviceUnderTest.getTypeByName(anyString());
        //then
        assertThat(obtainedTypeDict).isNotNull();
    }


}
