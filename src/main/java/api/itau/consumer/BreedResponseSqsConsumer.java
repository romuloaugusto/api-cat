package api.itau.consumer;

import api.itau.dto.BreedResponseSqsDto;
import api.itau.service.BreedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BreedResponseSqsConsumer {

    private final ObjectMapper objectMapper;

    private final BreedService breedService;

    @Value("${cloud.aws.end-point.uri-queue-response}")
    private String uriQueueResponse;

    @SqsListener(value = "${cloud.aws.end-point.uri-queue-response}")
    public void receiveMessage(BreedResponseSqsDto message) {
        try {
            log.info("Consumindo mensagem da fila {}", uriQueueResponse);
            log.debug("Conteudo da mensagem {} ", message);

            String json = objectMapper.writeValueAsString(message);

            // TODO: chamar serviço que envia email

            log.info("Email de resposta enviado para {}", message.getEmail());
            log.info("JSON enviado {} ", json);

            log.info("Mensagem consumida da fila {}", uriQueueResponse);
        } catch (Exception e) {
            log.error("Erro ao tentar processar mensagem {}. {} ", message, e);
        }
    }
}
