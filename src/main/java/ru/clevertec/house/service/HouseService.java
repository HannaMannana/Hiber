package ru.clevertec.house.service;

import ru.clevertec.house.dto.HouseCreateRequestDto;
import ru.clevertec.house.dto.HouseResponseDto;

import java.util.List;
import java.util.UUID;

public interface HouseService {

    HouseResponseDto getById(UUID uuid);

    List<HouseResponseDto> getAll();

    HouseResponseDto create(HouseCreateRequestDto dto);

    HouseResponseDto update(UUID uuid, HouseCreateRequestDto dto);

    void delete(UUID uuid);
}
