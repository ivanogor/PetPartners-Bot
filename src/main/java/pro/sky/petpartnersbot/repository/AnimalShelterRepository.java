package pro.sky.petpartnersbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.petpartnersbot.entity.AnimalShelter;

/**
 * Репозиторий для работы с приютами для животных.
 */
public interface AnimalShelterRepository extends JpaRepository<AnimalShelter, String> {
}