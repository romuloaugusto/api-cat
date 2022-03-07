package api.itau.service;

import api.itau.dto.BreedDto;
import api.itau.dto.BreedRequestDto;
import api.itau.dto.BreedRequestSqsDto;
import api.itau.dto.BreedResponseSqsDto;
import api.itau.dto.ImageDto;
import api.itau.entity.Breed;
import api.itau.enumeration.SearchType;
import api.itau.producer.SqsMessageProducer;
import api.itau.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BreedService {

    @Value("${cloud.aws.end-point.uri-queue-request}")
    private String uriQueueRequest;

    @Value("${cloud.aws.end-point.uri-queue-response}")
    private String uriQueueResponse;

    @Value("${x-api-key}")
    private String apiKey;

    private final SqsMessageProducer producer;

    private final BreedRepository breedRepository;

    private final BreedIntegrationService breedIntegrationService;

    private final SqsMessageProducer sqsMessageProducer;

    private final ModelMapper modelMapper;

    @Async
    public CompletableFuture createRequestAsync(SearchType searchType, String filterValue, BreedRequestDto request) {

        BreedRequestSqsDto sqsDto = BreedRequestSqsDto.builder()
                .email(request.getEmail())
                .searchType(searchType)
                .filterValue(filterValue)
                .build();

        producer.send(uriQueueRequest, sqsDto);

        log.info("Mensagem adicionada na fila {}", uriQueueRequest);
        log.debug("Mensagem {}  adicionada na fila {}", sqsDto, uriQueueRequest);

        return CompletableFuture.completedFuture("success");
    }

    public CompletableFuture createRequestAsync(SearchType searchType, BreedRequestDto request) {
        return createRequestAsync(searchType, null, request);
    }

    public void processRequestFromQueue(BreedRequestSqsDto message) {
        switch (message.getSearchType()) {
            case BREED_ALL:
                log.info("Processando busca por todas raças");
                processRequestSearchAll(message);
                log.info("Finalizou processamento da busca por todas raças");
                break;
            case BREED_BY_NAME:
                log.info("Processando busca por nome");
                processRequestSearchByName(message);
                log.info("Finalizou processamento da busca por nome");
                break;
            case BREED_BY_ORIGIN:
                log.info("Processando busca por origem");
                processRequestSearchByOrigin(message);
                log.info("Finalizou processamento da busca por origem");
                break;
            case BREED_BY_TEMPERAMENT:
                log.info("Processando busca por temperamento");
                processRequestSearchByTemperament(message);
                log.info("Finalizou processamento da busca por temperamento");
                break;
            default:
                break;
        }
    }

    private void processRequestSearchByName(BreedRequestSqsDto message) {
        var breedsDb = breedRepository.findAllByName(message.getFilterValue());
        List<BreedDto> breedDtos;
        if (!breedsDb.isEmpty()) {
            log.info("Busca pelo nome {} encontrou {} resultados no banco.", message.getFilterValue(), breedsDb.size());
            breedDtos = convertFromListBreedToListBreedDto(breedsDb);
        } else {
            breedDtos = breedIntegrationService.findAllByName(apiKey, message.getFilterValue());
            log.info("Busca pelo nome {} encontrou {} resultados na api externa.", message.getFilterValue(), breedDtos.size());
        }

        var breedResponseSqsDto = BreedResponseSqsDto.builder()
                .breeds(breedDtos)
                .email(message.getEmail())
                .count(breedDtos.size())
                .build();

        sqsMessageProducer.send(uriQueueResponse, breedResponseSqsDto);
        log.info("Mensagem adicionada na fila {} ", uriQueueResponse);
        log.debug("Dados da mensagem adicionada: {}", breedResponseSqsDto);
    }

    private void processRequestSearchByTemperament(BreedRequestSqsDto message) {
        var breedsDb = breedRepository.findAllByTemperament(message.getFilterValue());
        List<BreedDto> breedDtos = new ArrayList<>();
        if (!breedsDb.isEmpty()) {
            log.info("Busca por temperamento {} encontrou {} resultados no banco.", message.getFilterValue(), breedsDb.size());
            breedDtos = convertFromListBreedToListBreedDto(breedsDb);
        }

        var breedResponseSqsDto = BreedResponseSqsDto.builder()
                .breeds(breedDtos)
                .email(message.getEmail())
                .count(breedDtos.size())
                .build();

        sqsMessageProducer.send(uriQueueResponse, breedResponseSqsDto);
        log.info("Mensagem adicionada na fila {} ", uriQueueResponse);
        log.debug("Dados da mensagem adicionada: {}", breedResponseSqsDto);
    }

    private void processRequestSearchByOrigin(BreedRequestSqsDto message) {
        var breedsDb = breedRepository.findAllByOrigin(message.getFilterValue());
        List<BreedDto> breedDtos = new ArrayList<>();
        if (!breedsDb.isEmpty()) {
            log.info("Busca por origem {} encontrou {} resultados no banco.", message.getFilterValue(), breedsDb.size());
            breedDtos = convertFromListBreedToListBreedDto(breedsDb);
        }

        var breedResponseSqsDto = BreedResponseSqsDto.builder()
                .breeds(breedDtos)
                .email(message.getEmail())
                .count(breedDtos.size())
                .build();

        sqsMessageProducer.send(uriQueueResponse, breedResponseSqsDto);
        log.info("Mensagem adicionada na fila {} ", uriQueueResponse);
        log.debug("Dados da mensagem adicionada: {}", breedResponseSqsDto);
    }

    private void processRequestSearchAll(BreedRequestSqsDto message) {
        List<BreedDto> breedDtos = new ArrayList<>();

        var breedsDb = breedRepository.findAll();
        log.info("{} raças encontradas no banco", breedsDb.size());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (!breedsDb.isEmpty()) {
            for (Breed breed : breedsDb) {
                var breedDto = modelMapper.map(breed, BreedDto.class);
                breedDto.setImage(ImageDto.builder().url(breed.getImageUrl()).build());
                breedDtos.add(breedDto);
            }

            var breedResponseSqsDto = BreedResponseSqsDto.builder()
                    .breeds(breedDtos)
                    .email(message.getEmail())
                    .count(breedDtos.size())
                    .build();

            sqsMessageProducer.send(uriQueueResponse, breedResponseSqsDto);
            log.info("Nova Mensagem adicionada nada fila {}", uriQueueResponse);
            log.debug("Conteudo da mensagem {}", breedResponseSqsDto);
        } else {
            breedDtos = breedIntegrationService.findAllBreeds(apiKey);

            var breedResponseSqsDto = BreedResponseSqsDto.builder()
                    .breeds(breedDtos)
                    .email(message.getEmail())
                    .count(breedDtos.size())
                    .build();

            sqsMessageProducer.send(uriQueueResponse, breedResponseSqsDto);
            log.info("Nova Mensagem adicionada nada fila {}", uriQueueResponse);
            log.debug("Conteudo da mensagem {}", breedResponseSqsDto);

            loadBreedsInDatabase(breedDtos);
        }
    }

    public void loadBreedsInDatabase(List<BreedDto> breedsDto) {
        breedsDto.stream().forEach(breedDto -> {
            Breed breedFromDb = breedRepository.findByExternalId(breedDto.getExternalId());
            Breed breedToSaveOrUpdate = Breed.buildFromBreedDto(breedDto);

            if (breedFromDb != null) {
                breedToSaveOrUpdate.setId(breedFromDb.getId());
                breedRepository.saveAndFlush(breedToSaveOrUpdate);
                log.info("Raça {} atualizada no banco", breedToSaveOrUpdate.getName());
                log.debug("Dados da raça atualizados {}", breedToSaveOrUpdate);
            } else {
                breedRepository.saveAndFlush(breedToSaveOrUpdate);
                log.info("Raça {} salva no banco.", breedToSaveOrUpdate.getName());
                log.debug("Dados da raça salvos {}", breedToSaveOrUpdate);
            }
        });
    }

    private List<BreedDto> convertFromListBreedToListBreedDto(List<Breed> breeds) {
        List<BreedDto> result = new ArrayList<>();

        for (Breed breed : breeds) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            var breedDto = modelMapper.map(breed, BreedDto.class);
            breedDto.setImage(breed.getImageUrl() != null
                    ? ImageDto.builder().url(breed.getImageUrl()).build() : null);
            result.add(breedDto);
        }

        return result;
    }

}
