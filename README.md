# GreenDay 🌱

Plataforma de monitoramento agrícola inteligente: sensores IoT (ESP32), previsão do
tempo, alertas e irrigação automatizada.

## Stack
- Java 17 + Spring Boot 3.3
- Spring Security + JWT
- Spring Data JPA (H2 em dev / PostgreSQL em produção)
- Thymeleaf (páginas server-side)
- Gradle

## Como rodar

```bash
./gradlew bootRun
```

A aplicação sobe em `http://localhost:8080`.
Usuário admin padrão (dev): `admin@greenday.com` / `admin123` (criado via `data.sql`, se presente).

## Estrutura

- `config` – configuração de segurança e da API
- `controller` – endpoints REST e MVC
- `model` – entidades JPA
- `repository` – interfaces Spring Data
- `service` – regras de negócio
- `dto` – objetos de transferência
- `security` – JWT (geração/validação/filtro)
- `exception` – tratamento global de erros

## Principais domínios

| Entidade | Descrição |
|---|---|
| Usuario | Conta de acesso (produtor/admin) |
| Propriedade | Fazenda/sítio do usuário |
| Cultura | Cultivo plantado numa propriedade |
| Sensor / Esp32 | Dispositivo físico e sensor de leitura |
| DadosSensor | Série histórica de leituras |
| Clima | Dados meteorológicos da propriedade |
| Alerta | Notificações (seca, geada, praga, etc.) |
| Irrigacao | Eventos/agendamento de irrigação |
| Recomendacao | Sugestões geradas a partir dos dados |
