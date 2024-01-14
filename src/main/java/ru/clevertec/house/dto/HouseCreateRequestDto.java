package ru.clevertec.house.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HouseCreateRequestDto {

    private UUID uuid;

    private Double area;

    private String country;

    private String city;

    private String street;

    private Long number;
}
