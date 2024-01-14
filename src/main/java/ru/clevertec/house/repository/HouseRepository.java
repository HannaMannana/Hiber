package ru.clevertec.house.repository;

import ru.clevertec.house.entity.HouseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository {
    Optional<HouseEntity> findById(Long id);
    Optional<HouseEntity> findByUUID(UUID uuid);
    List<HouseEntity> findAll();
    Optional<HouseEntity> save(HouseEntity entity);
    Optional<HouseEntity> update (HouseEntity entity);
    void delete (UUID uuid);
}
