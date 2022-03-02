package api.itau.service;

import api.itau.dto.BreedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "breeds", url = "https://api.thecatapi.com/v1")
public interface BreedService {

    @GetMapping(value="/breeds")
    List<BreedDto> findAllBreeds(@RequestHeader("x-api-key") String token);

    @GetMapping(value="/breeds/search")
    List<BreedDto> findAllByName(@RequestHeader("x-api-key") String token,
                                 @RequestParam(name = "q", required = true) String name);


}
