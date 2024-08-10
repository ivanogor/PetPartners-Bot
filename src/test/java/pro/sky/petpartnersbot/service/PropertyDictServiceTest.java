package pro.sky.petpartnersbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.petpartnersbot.entity.PropertyDict;
import pro.sky.petpartnersbot.repository.PropertyDictRepository;
import pro.sky.petpartnersbot.service.impl.PropertyDictServiceImpl;
import pro.sky.petpartnersbot.util.PropertyDictUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PropertyDictServiceTest {
    @Mock
    private PropertyDictRepository propertyDictRepository;
    @InjectMocks
    private PropertyDictServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test find list of property dict by id functionality")
    public void givenId_whenFindByEntity_thenListOfPropertyDictIsReturned() {
        //given
        List<PropertyDict> propertyDicts = PropertyDictUtils.getPropertyDictList();
        BDDMockito.given(propertyDictRepository.getByEntityId(anyLong())).willReturn(propertyDicts);
        //when
        List<PropertyDict> obtainedPropertyDicts = serviceUnderTest.findByEntity(anyLong());
        //then
        assertThat(obtainedPropertyDicts).isNotNull();
        assertThat(obtainedPropertyDicts.size()).isEqualTo(propertyDicts.size());
    }

    @Test
    @DisplayName("Test find property dict by name and id functionality")
    public void givenNameAndId_whenFindByNameAndEntity_thenPropertyDictIsReturned() {
        //given
        PropertyDict propertyDict = PropertyDictUtils.getPropertyDict();
        BDDMockito.given(propertyDictRepository.getByNameAndEntityId(anyString(), anyLong())).willReturn(propertyDict);
        //when
        PropertyDict obtainedPropertyDict = serviceUnderTest.findByNameAndEntity(anyString(), anyLong());
        //then
        assertThat(obtainedPropertyDict).isNotNull();
    }

    @Test
    @DisplayName("Test find list of property dict by name and id functionality")
    public void givenEntityIdAndChatId_whenFindFilledByChatIdAndEntityId_thenListOfPropertyDictIsReturned() {
        //given
        List<PropertyDict> propertyDicts = PropertyDictUtils.getPropertyDictList();
        BDDMockito.given(propertyDictRepository.getChatIdFilled(anyLong(), anyLong())).willReturn(propertyDicts);
        //when
        List<PropertyDict> obtainedPropertyDicts = serviceUnderTest.findFilledByChatIdAndEntityId(anyLong(), anyLong());
        //then
        assertThat(obtainedPropertyDicts).isNotNull();
        assertThat(obtainedPropertyDicts.size()).isEqualTo(propertyDicts.size());
    }

    @Test
    @DisplayName("Test find list of property dict by entity id and pet id functionality")
    public void givenEntityIdAndPetId_whenFindFilledByEntityIdAndPetId_thenListOfPropertyDictIsReturned() {
        //given
        List<PropertyDict> propertyDict = PropertyDictUtils.getPropertyDictList();
        BDDMockito.given(propertyDictRepository.getFilledByEntityIdAndPetId(anyLong(), anyLong())).willReturn(propertyDict);
        //when
        List<PropertyDict> obtainedPropertyDict = serviceUnderTest.findFilledByEntityIdAndPetId(anyLong(), anyLong());
        //then
        assertThat(obtainedPropertyDict).isNotNull();
        assertThat(obtainedPropertyDict.size()).isEqualTo(propertyDict.size());
    }
}
