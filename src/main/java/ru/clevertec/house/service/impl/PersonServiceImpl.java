package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.house.dto.PersonCreateRequestDto;
import ru.clevertec.house.dto.PersonResponseDto;
import ru.clevertec.house.entity.HouseEntity;
import ru.clevertec.house.entity.PersonEntity;
import ru.clevertec.house.exeption.AppException;
import ru.clevertec.house.exeption.ApplicationException;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final HouseRepository houseRepository;
    private final PersonMapper personMapper;


    @Override
    public PersonResponseDto getById(UUID uuid) {
        try {
            return personRepository.findByUUID(uuid)
                    .map(personMapper::toDto)
                    .orElseThrow(() -> new AppException("None person with id" + uuid));
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<PersonResponseDto> getAll() {

        List<PersonEntity> personList = personRepository.findAll();

        List<PersonResponseDto> dtos = new ArrayList<>();
        for (PersonEntity person : personList) {
            PersonResponseDto dto = personMapper.toDto(person);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public PersonResponseDto create(PersonCreateRequestDto dto) throws ApplicationException {
        Optional<HouseEntity> optionalHouse = houseRepository.findByUUID(dto.getHouseId());
        Optional<HouseEntity> optionalOwnerHouse = houseRepository.findByUUID(dto.getOwnerHouseId());
        HouseEntity house = optionalHouse.orElseThrow(() -> new ApplicationException("House not found", HttpStatus.NOT_FOUND));

        PersonEntity entityToCreate = personMapper.createDtoToEntity(dto);
        entityToCreate.setHouse(house);

        if (optionalOwnerHouse.isPresent()) {
            List<HouseEntity> ownershipHouses = new ArrayList<>();
            ownershipHouses.add(optionalOwnerHouse.get());

            entityToCreate.setOwnershipHouses(ownershipHouses);
        }

        Optional<PersonEntity> person = personRepository.save(entityToCreate);
        return personMapper.toDto(person.orElseThrow());
    }

    @Override
    public PersonResponseDto update(UUID uuid, PersonCreateRequestDto dto) throws ApplicationException{

        Optional<HouseEntity> optionalHouse = houseRepository.findByUUID(dto.getHouseId());
        HouseEntity house = optionalHouse.orElseThrow(() -> new ApplicationException("House not found", HttpStatus.NOT_FOUND));

        PersonEntity toUpdate = personMapper.updateDtoToEntity(dto);

        PersonEntity person = personRepository.findByUUID(uuid).orElseThrow();

        toUpdate.setId(person.getId());
        toUpdate.setUuid(uuid);
        toUpdate.setHouse(house);
        toUpdate.setCreateDate(person.getCreateDate());

        Optional<PersonEntity> saved = personRepository.update(toUpdate);
        return personMapper.toDto(saved.orElseThrow());
    }

    @Override
    public void delete(UUID uuid) {

        personRepository.delete(uuid);
    }
}
