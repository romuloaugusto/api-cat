package api.itau.controller;

import api.itau.dto.BreedDto;
import api.itau.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cat")
public class BreedController {

    @Value("x-api-key")
    private String apiKey;

    private final BreedService breedService;

    @GetMapping("/breed")
    public ResponseEntity<List<BreedDto>> findAllBreeds() {
        List<BreedDto> breeds = breedService.findAllBreeds(apiKey);
        return new ResponseEntity<>(breeds, HttpStatus.OK);
    }

    @GetMapping("/breed/search")
    public ResponseEntity<List<BreedDto>> findAllByName(@RequestParam(name = "q", required = true) String name) {
        List<BreedDto> breeds = breedService.findAllByName(apiKey,  name);
        return new ResponseEntity<>(breeds, HttpStatus.OK);
    }



}
