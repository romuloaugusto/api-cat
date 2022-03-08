# Documentação do Projeto

### 1. Tecnologias Utilizadas:
Java 11, Spring Boot, Spring Data, Spring Cloud, Spring Boot Test, Lombok, Swagger, PostgreSQL, H2, AWS SQS.

### 2. Documentação (Swagger) da API:
http://ec2-3-138-107-27.us-east-2.compute.amazonaws.com:8080/swagger-ui/

### 3. Endpoints:

POST /api/v1/cat/breed/search

POST /api/v1/cat/breed/search/name

POST /api/v1/cat/breed/search/origin

POST /api/v1/cat/breed/search/temperament

### 4. Desenho Arquitetural:

![alt text](https://github.com/romuloaugusto/api-cat/blob/main/src/main/resources/img/desenho_arquitetura.PNG?raw=true)

3.1 Scheduler executado diariamente para salvar/atualizar a base, com o objetivo reduzir chamadas a API externa, reduzindo tráfego e obtendo uma melhor performance.

3.2 Cada requisição é salva na fila AWS SQS (Request) para ser processada posteriormente

3.3 O Consumer de Requests ler as mensagens da fila  AWS SQS (Request) e busca os dados no banco ou na API The Cat. Em seguida envia a resposta para a fila AWS SQS (Responses).

3.4 O Consumer de Responses ler as mensagens da fila AWS SQS (Responses) para em seguida enviar o resultado por e-mail para o cliente.

3.5 O conceito de Threads é aplicado nos endpoints através do uso de CompletableFuture disponível na Concurrency API do Java. 

### 5. Git
O projeto está disponível no github em https://github.com/romuloaugusto/api-cat

### 6. Como executar o projeto
1. Clone do projeto https://github.com/romuloaugusto/api-cat
2. Abrir o projeto em qualquer IDE de sua preferência
3. Executar a classe ItauApplication

#### P.S 1: A infraestrutura (banco e filas) já estão rodando na nuvem não sendo necessário nenhuma configuração adicional.

#### P.S 2: O serviço de envio de e-mail não foi implementado, a título de demonstraçao estou apenas logando no console o envio de e-mail.
