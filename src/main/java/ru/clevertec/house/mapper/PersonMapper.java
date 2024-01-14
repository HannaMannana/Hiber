package ru.clevertec.house.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.house.dto.PersonCreateRequestDto;
import ru.clevertec.house.dto.PersonResponseDto;
import ru.clevertec.house.entity.PersonEntity;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    PersonResponseDto toDto(PersonEntity entity);

    PersonEntity toEntity(PersonResponseDto dto);

    PersonEntity createDtoToEntity(PersonCreateRequestDto dto);

    PersonEntity updateDtoToEntity(PersonCreateRequestDto dto);
}
