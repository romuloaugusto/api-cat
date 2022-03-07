package api.itau.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SqsMessageProducer {

    private final QueueMessagingTemplate queueMessagingTemplate;

    public <T> void send(String endPoint, T message, Map<String, Object> headers) {
        if (message == null) {
            log.warn("SQS Producer não pode produzir valor 'null' ");
            return;
        }

//        log.debug(" Mensagem: {} " + message);
//        log.debug(" Nome da fila: {} " + endPoint);
        queueMessagingTemplate.convertAndSend(endPoint, message);
    }

    public <T> void send(String endPoint, T message) {
        send(endPoint, message, null);
    }

}

