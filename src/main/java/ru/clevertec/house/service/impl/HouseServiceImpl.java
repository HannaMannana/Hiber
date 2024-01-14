package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.house.dto.HouseCreateRequestDto;
import ru.clevertec.house.dto.HouseResponseDto;
import ru.clevertec.house.dto.PersonCreateRequestDto;
import ru.clevertec.house.entity.HouseEntity;
import ru.clevertec.house.entity.PersonEntity;
import ru.clevertec.house.exeption.AppException;
import ru.clevertec.house.exeption.ApplicationException;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.mapper.HouseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;


    @Override
    public HouseResponseDto getById(UUID uuid) {
        try {
            return houseRepository.findByUUID(uuid)
                    .map(houseMapper::toDto)
                    .orElseThrow(() -> new AppException("None person with id" + uuid));
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<HouseResponseDto> getAll() {

        List<HouseEntity> houseList = houseRepository.findAll();

        List<HouseResponseDto> dtos = new ArrayList<>();
        for (HouseEntity house : houseList) {
            HouseResponseDto dto = houseMapper.toDto(house);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public HouseResponseDto create(HouseCreateRequestDto createDto) {
        Optional<HouseEntity> house = houseRepository.save(houseMapper.createDtoToEntity(createDto));
        return houseMapper.toDto(house.orElseThrow());
    }

    @Override
    public HouseResponseDto update(UUID uuid, HouseCreateRequestDto dto) {

        HouseEntity toUpdate = houseMapper.updateDtoToEntity(dto);
        HouseEntity house = houseRepository.findByUUID(uuid).orElseThrow();

        toUpdate.setId(house.getId());
        toUpdate.setUuid(uuid);
        toUpdate.setCountry(house.getCountry());
        toUpdate.setCity(house.getCity());
        toUpdate.setCreateDate(house.getCreateDate());

        Optional<HouseEntity> saved = houseRepository.update(toUpdate);
        return houseMapper.toDto(saved.orElseThrow());
    }

    @Override
    public void delete(UUID uuid) {

        Optional<HouseEntity> house = houseRepository.findByUUID(uuid);

        if ((house.get().getTenants() != null) && (house.get().getOwners() != null)) {
            throw new ApplicationException("House can't delete", HttpStatus.BAD_REQUEST);
        } else {
            houseRepository.delete(uuid);
        }
    }


}
