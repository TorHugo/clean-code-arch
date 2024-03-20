# Clean Code/ Clean Architecture.
Este repositório contém dois projetos backend (account-backend, ride-backend) com boundcontexts distintos. Importante enfatizar o uso de DDD (Domain-Driven Design) e Clean Architecture em ambos projetos.
<br>
## Arquitetura e Design
DDD e Clean Architecture são abordagens que visam criar ‘software’ robusto e escalável, focando na lógica de negócios e na separação de preocupações. Domain-Driven ‘Design’ garante que o ‘software’ reflita com precisão o domínio, enquanto Clean Architecture promove a manutenibilidade e a testabilidade. Juntos, eles facilitam a evolução do ‘software’ e melhoram a comunicação entre desenvolvedores e especialistas do domínio.

### DDD (Domain-Driven Design)
Domain-Driven ‘Design’, é uma abordagem de ‘design’ que se concentra na lógica de negócios e na complexidade do domínio. Ele ajuda a garantir que o ‘software’ reflita com precisão o domínio do problema que está a ser resolvido.

- **Domínio**: Representa o conhecimento e as regras do negócio.
- **Subdomínios**: Partes do domínio que podem ser desenvolvidas e mantidas de forma independente.
- **Modelos de Domínio**: Representações abstratas do domínio usadas para resolver problemas do negócio.

### Clean Architecture
Clean Architecture é uma abordagem de ‘design’ que separa as preocupações do ‘software’ em camadas distintas, facilitando a manutenção e a escalabilidade.

- **Entities**: Representam os objetos de negócio e suas regras.
- **Use Cases**: Contêm a lógica de aplicação que atua sobre as entidades.
- **‘Interface’ Adapters**: Convertem os dados entre os formatos usados pelo ‘software’ e os formatos usados pelo domínio.
- **‘Frameworks’ and Drivers**: Contêm detalhes específicos sobre a infraestrutura e tecnologias usadas.

## Tecnologias
- Java 17
- SpringBoot (3.1.8)
- PostgreSQL
- RabbitMq

## account-backend
Este boundcontext trata de ações específicas de accounts. Ele contém dois use-cases cores da aplicação:
- SignUp
- GetAccount

### Endpoints
- Endpoint responsável por criar uma conta.
```http request
POST api/acccounts/signup
```
**Body**
```json
{
    "name": "Account Test",
    "email": "account_test@dev.com.br",
    "cpf": "648.808.745-23",
    "car_plate": "ABC1234",
    "is_passenger": true,
    "is_driver": false
}
```
**Response**:
```json
{
  "account_id": "6fbf7018-0b4d-4311-b16c-75b9a0556266"
}
```
***
- Endpoint responsável por recuperar uma conta.
```http request
GET api/acccounts/get-account/{accountId}
```
| Parameter          | Type   | Example                              |
|:-------------------|:-------|:-------------------------------------|
| `accountId`        | `UUID` | 6fbf7018-0b4d-4311-b16c-75b9a0556266 |
**Response**:
```json
{
  "account_id": "6fbf7018-0b4d-4311-b16c-75b9a0556266",
  "name": "Account Test",
  "email": "account.passenger@dev.com",
  "car_plate": "ABC1234",
  "cpf": "648.808.745-23",
  "is_passenger": true,
  "created_at": "2024-03-17T03:25"
}
```
***
- Endpoint responsável por criar uma nova conta de forma assíncrona (Event-Driven).
```http request
POST api/acccounts/signup-async
```
**Body**
```json
{
  "name": "Account Test",
  "email": "account_test@dev.com.br",
  "cpf": "648.808.745-23",
  "car_plate": "ABC1234",
  "is_passenger": true,
  "is_driver": false
}
```

### Queues
| Name                | QUEUE_SIGNUP_ASYNC     |
|:--------------------|:-----------------------|
| Name                | QUEUE_SIGN_UP_WELCOME  |

## ride-backend
Este boundcontext trata de ações específicas de rides. Ele contém, alguns use-cases, como:
- AcceptRide
- GetRide
- StartRide
- UpdatePosition
- GetAllPositions
- FinishRide

### Endpoints
- Endpoint responsável por solicitar uma corrida.
```http request
POST api/rides/request
```
**Body**
```json
{
  "passenger_id": "6fbf7018-0b4d-4311-b16c-75b9a0556266",
  "from": {
    "latitude": -41.1239123892,
    "longitude": -21.329832983
  },
  "to": {
    "latitude": -38.1239123892,
    "longitude": -19.329832983
  }
}
```
**Response**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60"
}
```
***
- Endpoint responsável por aceitar uma corrida.
```http request
PUT api/rides/accept
```
**Body**:
```json
{
    "ride_id": "dab8ff06-c197-4fe7-87aa-791962bf7424",
    "driver_id": "96fa17d2-b8e1-4661-968f-8cb9f866964e"
}
```
**Response**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60"
}
```
***
- Endpoint responsável por recuperar uma corrida.
```http request
GET api/rides/{rideId}
```
| Parameter         | Type   | Example                              |
|:------------------|:-------|:-------------------------------------|
| `rideId`          | `UUID` | 6fbf7018-0b4d-4311-b16c-75b9a0556266 |
**Response**:
```json
{
  "ride_id": "6e34a034-d59e-4e8e-9e86-3815a1a6f9ff",
  "passenger": {
    "account_id": "4bb94abc-205c-4aac-b3a2-e5d8d52e474d",
    "name": "passenger account",
    "email": "passenger@test.com",
    "is_passenger": true,
    "car_plate": "ABC1234"
  },
  "driver": {
    "account_id": "3as12ocd-205c-4aac-b3a2-e5d8d52e474d",
    "name": "driver account",
    "email": "driver@test.com",
    "is_driver": true,
    "car_plate": "ABC1234"
  },
  "status": "ACCEPTED",
  "fare": null,
  "distance": null,
  "from": {
    "latitude": 0.9992799568940082,
    "longitude": 0.4739974532248221
  },
  "to": {
    "latitude": 0.9992799568940082,
    "longitude": 0.4739974532248221
  },
  "last_position": {
    "latitude": 0.9992799568940082,
    "longitude": 0.4739974532248221
  },
  "created_at": "2024-03-18T16:06"
}
```
***
- Endpoint responsável por iniciar uma corrida.
```http request
PUT api/rides/start/{rideId}
```
| Parameter         | Type   | Example                              |
|:------------------|:-------|:-------------------------------------|
| `rideId`          | `UUID` | 784603a5-2123-4914-b670-8b4225420a60 |
**Response**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60"
}
```
***
- Endpoint responsável por atualizar a posição de uma corrida.
```http request
PUT api/positions/update
```
**Body**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60",
  "latitude": -18.1239123892,
  "longitude": -7.329832983
}
```
**Response**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60"
}
```
***
- Endpoint responsável por finalizar uma corrida.
```http request
PUT api/rides/finish/{rideId}
```
| Parameter         | Type   | Example                              |
|:------------------|:-------|:-------------------------------------|
| `rideId`          | `UUID` | 784603a5-2123-4914-b670-8b4225420a60 |
**Response**:
```json
{
  "ride_id": "784603a5-2123-4914-b670-8b4225420a60"
}
```
***
- Endpoint responsável por recuperar uma corrida.
```http request
GET api/positions/all/{rideId}
```
| Parameter         | Type   | Example                              |
|:------------------|:-------|:-------------------------------------|
| `rideId`          | `UUID` | 784603a5-2123-4914-b670-8b4225420a60 |
**Response**:
```json
{
    "ride_id": "784603a5-2123-4914-b670-8b4225420a60",
    "positions": [
        {
            "coordinates": {
                "latitude": 0.6536335812006681,
                "longitude": 0.8475396474335144
            },
            "created_at": "2024-03-18T20:27"
        }
    ]
}
```

### Queues
| Name                | QUEUE_PROCESS_PAYMENT  |
|:--------------------|:-----------------------|

## Agradecimentos
O meu principal agradecimento vai para o professor Rodrigo Branas. Não só por sua didática incrivel e os seus ótimos exemplos, ou até mesmo por compartilhar o seu vasto conhecimento conosco. Mas também, e principalmente, por despertar em mim, um olhar mais crítico sobre o que eu densenvolvo. <br/> <br/>
A partir do curso de Clean Code/Architecture, eu pude perceber o quão importante é para um ‘software’ uma boa definição de arquitetura de projeto (que não se limita apenas há uma divisão de camadas). E não só isso, a separação que o Domain-Drive Disgn traz consigo, do objeto de domínio com as suas regras de negócio idenpendentes, faz total sentido pensando numa aplicação que possui um grande potencial escalavel. <br/><br/>
E não menos importante, a facilidade, testabilidade e manutenabilidade que o Clean Architecture nos disponibiliza. Os conceitos de S.O.L.I.D nunca foram tão claros para mim. Sem contar na gama de Design Patterns que foi disponibilizado para o meu aprendizado! 

## Contato
- LinkedIn: https://www.linkedin.com/in/victorhugodev/
- Email: contato.arrudavictor@gmail.com
- Beacons: https://beacons.ai/tor_hugo
