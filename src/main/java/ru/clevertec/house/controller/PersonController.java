package ru.clevertec.house.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.house.dto.PersonCreateRequestDto;
import ru.clevertec.house.dto.PersonResponseDto;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{uuid}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable("uuid") UUID uuid) {
        PersonResponseDto personResponseDto = personService.getById(uuid);
        return ResponseEntity.ok(personResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<PersonResponseDto>> findAll() {

        List<PersonResponseDto> allPersons = personService.getAll();
        return ResponseEntity.ok(allPersons);
    }

    @PostMapping()
    public ResponseEntity<PersonResponseDto> create(@RequestBody PersonCreateRequestDto createRequestDto) {
        PersonResponseDto createdPerson = personService.create(createRequestDto);
        return ResponseEntity.ok(createdPerson);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable("uuid") UUID uuid, @RequestBody PersonCreateRequestDto updateRequestDto) {
        return ResponseEntity.ok(personService.update(uuid, updateRequestDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
        personService.delete(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


