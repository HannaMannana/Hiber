package ru.clevertec.house.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.house.dto.HouseCreateRequestDto;
import ru.clevertec.house.dto.HouseResponseDto;
import ru.clevertec.house.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponseDto> findById(@PathVariable("uuid") UUID uuid) {
        HouseResponseDto houseResponseDto = houseService.getById(uuid);
        return ResponseEntity.ok(houseResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<HouseResponseDto>> findAll() {
        List<HouseResponseDto> allHouses = houseService.getAll();
        return ResponseEntity.ok(allHouses);
    }

    @PostMapping()
    public ResponseEntity<HouseResponseDto> create(@RequestBody HouseCreateRequestDto createRequestDto) {
        HouseResponseDto createdHouse = houseService.create(createRequestDto);
        return ResponseEntity.ok(createdHouse);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<HouseResponseDto> update(@PathVariable("uuid") UUID uuid, @RequestBody HouseCreateRequestDto updateRequestDto) {
        return ResponseEntity.ok(houseService.update(uuid, updateRequestDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
