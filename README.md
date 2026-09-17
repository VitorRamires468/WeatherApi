# 🌤️ Weather API with Spring Boot & Redis

Uma API REST desenvolvida em Java com Spring Boot para consultar dados meteorológicos de qualquer localização. A aplicação consome uma API externa de terceiros (Visual Crossing) e utiliza **Redis** para armazenamento em cache, otimizando o tempo de resposta e reduzindo chamadas externas repetidas. O projeto também conta com suporte a **Rate Limiting** para prevenção de abusos.

---

## 🚀 Funcionalidades

- **Consulta do Clima**: Retorna temperatura atual, máxima e mínima para determinada cidade, estado e país.
- **Estratégia de Cache (Cache-Aside)**: Armazena o resultado no Redis com tempo de expiração (TTL) configurável, evitando requisições desnecessárias à API externa.
- **Validação de Localização**: Validação estrita dos dados retornados para garantir a correspondência geográfica entre cidade, estado e país.
- **Tratamento Global de Erros**: Respostas HTTP padronizadas (ex: `404 Not Found`, `500 Internal Error`) gerenciadas via `@RestControllerAdvice`.
- **Rate Limiting**: Proteção contra excesso de requisições por IP utilizando o algoritmo *Token Bucket* via biblioteca **Bucket4j**.
- **Gerenciamento Seguro de Credenciais**: Utilização de variáveis de ambiente no `application.yaml` para proteger chaves e configurações sensíveis.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
    - *Spring Web*
    - *Spring Data Redis*
- **Redis**: Banco de dados em memória para cache distribuído.
- **RestClient**: Cliente HTTP moderno e fluente para consumo de APIs de terceiros.
- **Bucket4j**: Implementação de Rate Limiting por IP.
- **Jackson**: Serialização e desserialização de objetos Java para JSON.

---

## 📋 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
- **JDK 17** ou superior instalado.
- **Maven** para gerenciamento de dependências.
- Instância do **Redis** em execução na porta padrão `6379` (seja nativo, via WSL2 ou Docker).
- Uma **API Key** gratuita da [Visual Crossing Weather API](https://www.visualcrossing.com/weather-api).

---

## ⚙️ Configuração e Instalação

### 1. Clonar o Repositório
```bash
git clone [https://github.com/seu-usuario/nome-do-repositorio.git](https://github.com/seu-usuario/nome-do-repositorio.git)
cd nome-do-repositorio
````

### 2. Configurar Variáveis de Ambiente
A aplicação utiliza variáveis de ambiente para carregar a chave de acesso da API e conexões com o Redis. 

Você pode defini-las no seu sistema ou configurá-las diretamente na sua IDE (IntelliJ / VS Code / Eclipse):

| Variável | Descrição | Valor Padrão (Fallback) |
| :--- | :--- |:------------------------|
| `API_KEY` | **(Obrigatório)** Sua chave na Visual Crossing | -                       |
| `REDIS_HOST` | Host de conexão do Redis | `localhost`             |
| `REDIS_PORT` | Porta de conexão do Redis | `6379`                  |
| `REDIS_PASSWORD` | Senha do Redis (se houver) | *Vazio*                 |
| `WEATHER_CACHE_TTL_HOURS` | Tempo de vida do Cache no Redis (Horas) | `12`                    |

---

### 3. Executar o Redis
Caso utilize o Docker para rodar o Redis localmente:
```bash
docker run -d --name redis-local -p 6379:6379 redis