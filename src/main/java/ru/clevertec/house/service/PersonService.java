package ru.clevertec.house.service;

import ru.clevertec.house.dto.PersonCreateRequestDto;
import ru.clevertec.house.dto.PersonResponseDto;
import ru.clevertec.house.exeption.ApplicationException;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    PersonResponseDto getById(UUID uuid);

    List<PersonResponseDto> getAll();

    PersonResponseDto create(PersonCreateRequestDto dto) throws ApplicationException;

    PersonResponseDto update(UUID uuid,PersonCreateRequestDto dto);

    void delete(UUID uuid);
}
