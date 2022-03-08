package api.itau.controller;

import api.itau.controller.doc.BreedControllerDoc;
import api.itau.dto.BreedRequestDto;
import api.itau.enumeration.SearchType;
import api.itau.service.BreedIntegrationService;
import api.itau.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cat")
public class BreedController implements BreedControllerDoc {

    @Value("x-api-key")
    private String apiKey;

    private final BreedIntegrationService breedIntegrationService;

    private final BreedService breedService;

    @PostMapping("/breed/search")
    public @ResponseBody CompletableFuture<ResponseEntity> createSearchOfAllBreedsAsync(@RequestBody BreedRequestDto breedRequestDto) {
        return breedService.createRequestAsync(SearchType.BREED_ALL, breedRequestDto)
                .<ResponseEntity>thenApply(result -> new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping("/breed/search/name")
    public @ResponseBody CompletableFuture<ResponseEntity> createSearchByNameAsync(@RequestParam(value = "q", required = true)  String name,
                                                           @RequestBody BreedRequestDto breedRequestDto) {
        return breedService.createRequestAsync(SearchType.BREED_BY_NAME, name, breedRequestDto)
                .<ResponseEntity>thenApply(result -> new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping("/breed/search/temperament")
    public @ResponseBody CompletableFuture<ResponseEntity> createSearchByTemperamentAsync(@RequestParam(value = "q", required = true) String temperament,
                                                        @RequestBody BreedRequestDto breedRequestDto) {
        return breedService.createRequestAsync(SearchType.BREED_BY_TEMPERAMENT, temperament, breedRequestDto)
                .<ResponseEntity>thenApply(result -> new ResponseEntity<>(HttpStatus.CREATED));
    }

    @PostMapping("/breed/search/origin")
    public @ResponseBody CompletableFuture<ResponseEntity> createSearchByOriginAsync(@RequestParam(value = "q", required = true) String origin,
                                                        @RequestBody BreedRequestDto breedRequestDto) {
        return breedService.createRequestAsync(SearchType.BREED_BY_ORIGIN, origin, breedRequestDto)
                .<ResponseEntity>thenApply(result -> new ResponseEntity<>(HttpStatus.CREATED));
    }





}
