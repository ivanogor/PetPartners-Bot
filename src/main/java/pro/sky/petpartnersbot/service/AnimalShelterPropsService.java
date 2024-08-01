package pro.sky.petpartnersbot.service;

import pro.sky.petpartnersbot.entity.AnimalShelterProps;

import java.util.List;

public interface AnimalShelterPropsService {
    AnimalShelterProps getUserProp(Long propId, Long animalShelterId);
    List<AnimalShelterProps> getShltListProps(Long animalShelterId);
}
