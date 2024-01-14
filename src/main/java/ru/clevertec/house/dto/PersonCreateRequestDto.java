package ru.clevertec.house.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.house.entity.PersonEntity;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PersonCreateRequestDto {

    private UUID uuid;

    private String name;

    private String surname;

    private String passportSeries;

    private String passportNumber;

    private PersonEntity.Sex sexType;

    private UUID houseId;

    private UUID ownerHouseId;
}
