```markdown
# Desafio Anlix

  - [Sobre o desafio](#sobre-o-desafio)
    - [Rest API](#rest-api)
    - [Dashboard](#dashboard)
  - [Utilização](#utilização)
    - [Executar aplicação](#executar-aplicação)
    - [Endpoints da API](#endpoints-da-api)
    - [Testes de API](#testes-de-api)
    - [Tecnologias Utilizadas](#tecnologias-utilizadas)

## Sobre o desafio

### Rest API

Temos uma série de arquivos de texto localizados no diretório dados que contêm informações diversas sobre pacientes terminais internados em um hospital. Esses arquivos são fornecidos por diferentes departamentos e, por isso, sempre vêm separadamente. Cada arquivo dentro de um departamento tem datas distintas, o que representa as características dos pacientes em diferentes momentos. Precisamos desenvolver um software com uma base de dados que possa ser consultada por meio de uma API REST para:

* [X] Consultar individualmente as características mais recentes de cada paciente;

  > `GET /api/pacientes/{id}/caracteristicas/{tipo}/recent`

* [X] Consultar todas as características de um paciente em uma única chamada, mostrando os valores mais recentes de cada uma;

  > `GET /api/pacientes/{id}/caracteristicas/recentes`

* [X] Consultar todas as características de todos os pacientes para uma data específica;

  > `GET /api/pacientes/data/{data}`

* [X] Consultar uma característica específica de um paciente para um intervalo de datas;

  > `GET /api/pacientes/{id}/caracteristicas/{tipo}/data/{startDate}/{endDate}`

* [X] Consultar o valor mais recente de uma característica de um paciente que esteja dentro de um intervalo de valores especificado;

  > `GET /api/pacientes/{id}/caracteristicas/{tipo}/valor/{startValue}/{endValue}`

* [X] Buscar pacientes pelo nome ou parte do nome especificado;

  > `GET /api/pacientes/buscar/{nome}`

### Dashboard

Além da API, é necessário disponibilizar algumas informações em uma interface web. Os requisitos são:

* [ ] Buscar um paciente por nome e exibir o valor mais recente de cada uma de suas características;
* [ ] Exibir um gráfico temporal para um paciente e uma característica específica, através da interface;
* [ ] Exportar as características de um ou mais pacientes para um arquivo CSV com todas as datas disponíveis.

## Utilização

### Executar aplicação

Para iniciar todos os recursos necessários da aplicação, execute:

```shell
docker-compose up -d
```

A importação dos dados ocorrerá automaticamente ao iniciar a aplicação, utilizando o arquivo `pacientes.json` no diretório correto.

### Endpoints da API

- `GET /api/pacientes` - Listar todos os pacientes.
- `GET /api/pacientes/{id}` - Recuperar um paciente específico por ID.
- `POST /api/pacientes` - Criar um novo paciente.
- `DELETE /api/pacientes/{id}` - Excluir um paciente por ID.
- `GET /api/pacientes/{id}/caracteristicas` - Recuperar todas as características de um paciente específico.
- `GET /api/pacientes/{id}/caracteristicas/recentes` - Recuperar as características mais recentes de um paciente específico.
- `GET /api/pacientes/data/{data}` - Recuperar todas as características de todos os pacientes para uma data específica.
- `GET /api/pacientes/{id}/caracteristicas/{tipo}/data/{startDate}/{endDate}` - Recuperar características de um paciente para um intervalo de datas.
- `GET /api/pacientes/{id}/caracteristicas/{tipo}/valor/{startValue}/{endValue}` - Recuperar o valor mais recente de uma característica de um paciente que esteja dentro de um intervalo de valores.
- `GET /api/pacientes/buscar/{nome}` - Buscar pacientes por nome ou parte de um nome.
- `GET /api/pacientes/export/csv` - Exportar dados dos pacientes para um arquivo CSV.

### Testes de API

Os testes de API foram realizados utilizando Postman.

### Tecnologias Utilizadas

- **Java**: Linguagem de programação principal para o desenvolvimento do backend.
- **Spring Boot**: Framework para simplificar a configuração e o desenvolvimento de aplicações Java.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações dos pacientes.
- **Docker**: Ferramenta de conteinerização utilizada para criar ambientes isolados para execução da aplicação e banco de dados.
- **React**: Biblioteca JavaScript utilizada para construir a interface web.
- **OpenCSV**: Biblioteca para manipulação de arquivos CSV.
- **Postman**: Ferramenta utilizada para testar os endpoints da API.
- **IntelliJ IDEA**: Ambiente de desenvolvimento integrado (IDE) utilizado para o desenvolvimento do projeto.

#### Versões das Tecnologias

- **Java**: 17
- **Spring Boot**: 2.6.4
- **PostgreSQL**: 13
- **Docker**: 20.10
- **React**: 17.0.2
- **OpenCSV**: 5.5
- **Postman**: 8.0
- **IntelliJ IDEA**: 2021.1
```
