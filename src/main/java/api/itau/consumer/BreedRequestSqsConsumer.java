package api.itau.consumer;

import api.itau.dto.BreedRequestSqsDto;
import api.itau.service.BreedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BreedRequestSqsConsumer {

    private final BreedService breedService;

    @Value("${cloud.aws.end-point.uri-queue-request}")
    private String uriQueueResquest;

    @SqsListener(value = "${cloud.aws.end-point.uri-queue-request}")
    public void receiveMessage(BreedRequestSqsDto message) {
        try {
            log.info("Consumindo mensagem da fila {} ", uriQueueResquest);
            log.debug("Conteudo da mensagem {} ", message);
            breedService.processRequestFromQueue(message);
            log.info("Mensagem consumida com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao tentar processar mensagem {}. {} ", message, e);
        }
    }
}
