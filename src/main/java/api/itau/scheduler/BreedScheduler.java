package api.itau.scheduler;

import api.itau.dto.BreedDto;
import api.itau.service.BreedIntegrationService;
import api.itau.service.BreedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BreedScheduler {

    @Value("${x-api-key}")
    private String apiKey;

    private final ModelMapper modelMapper;

    private final BreedIntegrationService breedIntegrationService;

    private final BreedService breedService;


    //    @Scheduled(fixedDelayString = "${timer-scheduled}")
    @Scheduled(fixedDelayString = "60000")
    public void loadBreedDatabase() {
        log.info("Scheduler esta rodando");
        List<BreedDto> allBreedsDto = breedIntegrationService.findAllBreeds(apiKey);

        log.debug("Total de raças retornadas da api-cat: {} ", allBreedsDto.size());

        breedService.loadBreedsInDatabase(allBreedsDto);

        log.info("Scheduler concluído.");

    }

}
