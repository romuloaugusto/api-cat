# Documentação do Projeto


### 1. Tecnologias Utilizadas:
Java 11, Spring Boot, Spring Data, Spring Cloud, Spring Boot Test, Lombok, Swagger, PostgreSQL, H2, AWS SQS

### 2. Documentação (Swagger) da API:
URL: http://ec2-3-138-107-27.us-east-2.compute.amazonaws.com:8080/swagger-ui/

### 3. Desenho Arquitetural:

3.1 Scheduler executado diariamente para salvar/atualizar a base, com o objetivo reduzir chamadas a API externa, reduzindo tráfego e obtendo uma melhor performance.

3.2 Cada requisição é salva na fila AWS SQS para ser processada posteriormente

3.3 O consumer 1 ler as mensagens da fila 1 e busca os dados no banco ou na api externa. Em seguida envia a resposta para um outro AWS SQS.

3.4 O consumer 2 ler as mensagens da fila 2 para em seguida enviar o resultado por e-mail para o cliente.

3.5 O conceito de threads é aplicado nos endpoints através do uso de CompletableFuture disponível na Concurrency API do Java. 


### 4. Git
O projeto está disponível no github em https://github.com/romuloaugusto/api-cat

### 5. Como executar o projeto
1. Clone do projeto https://github.com/romuloaugusto/api-cat
2. Abrir o projeto em qualquer IDE de sua preferência
3. Executar a classe ItauApplication

P.S: A infraestrutura (banco e filas) já estão rodando na nuvem não sendo necessário nenhuma configuração adicional. 
