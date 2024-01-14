package ru.clevertec.house.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.house.dto.HouseCreateRequestDto;
import ru.clevertec.house.entity.HouseEntity;
import ru.clevertec.house.dto.HouseResponseDto;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HouseMapper {

    HouseResponseDto toDto(HouseEntity entity);

    HouseEntity toEntity(HouseResponseDto dto);

    HouseEntity createDtoToEntity(HouseCreateRequestDto createDto);

    HouseEntity updateDtoToEntity(HouseCreateRequestDto createDto);
}
