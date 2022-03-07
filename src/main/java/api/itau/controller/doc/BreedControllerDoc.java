package api.itau.controller.doc;

import api.itau.dto.BreedRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CompletableFuture;

public interface BreedControllerDoc {

    @Operation(description = "Serviço responsável por criar um pedido para listar todas as raças ")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Busca por todos criada") })
    CompletableFuture<ResponseEntity> createSearchOfAllBreedsAsync(@RequestBody BreedRequestDto breedRequestDto);

    @Operation(description = "Serviço responsável por criar um pedido para listar todas as raças a partir de um nome ")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Busca por nome criada") })
    @PostMapping("/breed/search/name")
    CompletableFuture<ResponseEntity> createSearchByNameAsync(@RequestParam(value = "q", required = true)  String name,
                                                             @RequestBody BreedRequestDto breedRequestDto);

    @Operation(description = "Serviço responsável por criar um pedido para listar todas as raças a partir de um temperamento")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Busca por temperamento criada") })
    @PostMapping("/breed/search/temperament")
    CompletableFuture<ResponseEntity> createSearchByTemperamentAsync(@RequestParam(value = "q", required = true) String temperament,
                                                               @RequestBody BreedRequestDto breedRequestDto);

    @Operation(description = "Serviço responsável por criar um pedido para listar todas as raças a partir de uma origem")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Busca por origem criada") })
    @PostMapping("/breed/search/origin")
    CompletableFuture<ResponseEntity> createSearchByOriginAsync(@RequestParam(value = "q", required = true) String origin,
                                                          @RequestBody BreedRequestDto breedRequestDto);

}
