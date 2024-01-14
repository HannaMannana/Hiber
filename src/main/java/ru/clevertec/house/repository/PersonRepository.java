package ru.clevertec.house.repository;

import ru.clevertec.house.entity.PersonEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository  {
    Optional<PersonEntity> findById(Long id);
    Optional<PersonEntity> findByUUID(UUID uuid);
    List<PersonEntity> findAll();
    Optional<PersonEntity> save(PersonEntity entity);
    Optional<PersonEntity> update (PersonEntity entity);
    void delete (UUID uuid);
}
