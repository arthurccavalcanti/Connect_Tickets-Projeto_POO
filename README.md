# Connect Tickets — Arena Pernambuco

Sistema web que conecta a população à Arena Pernambuco, centralizando divulgação de eventos, participação do público, sugestões e agendamento de visitas.

**API deployada:** https://tickets-poo.onrender.com  
**Documentação interativa:** https://tickets-poo.onrender.com/docs

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.4.4 |
| Segurança | Spring Security + JWT (HMAC256, 2h) |
| Persistência | Spring Data JPA + Hibernate |
| Banco de Dados | Supabase (PostgreSQL) |
| Documentação | SpringDoc OpenAPI + Scalar UI |
| Build | Maven |

---

## Como Executar

```bash
git clone https://github.com/brunosm26/Connect_Tickets-Projeto_POO.git
cd Connect_Tickets-Projeto_POO

# Mac/Linux
chmod +x mvnw
./mvnw spring-boot:run
```

> Por padrão sobe na porta `8080`. Em produção a porta é definida pela variável `PORT`.

---

## Endpoints

Após o login, envie o token em todas as requisições autenticadas:
```
Authorization: Bearer {token}
```

### Autenticação

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/register-admin` | ADMIN |

### Eventos

| Método | Rota | Acesso |
|---|---|---|
| GET | `/api/eventos` | Público |
| GET | `/api/eventos/{id}` | Público |
| GET | `/api/eventos?categoria={categoria}` | Público |
| POST | `/api/eventos` | ADMIN |
| PUT | `/api/eventos/{id}` | ADMIN |
| DELETE | `/api/eventos/{id}` | ADMIN |

> Categorias: `ESPORTE`, `SHOW`, `CULTURAL`, `CORPORATIVO`

### Usuários

| Método | Rota | Acesso |
|---|---|---|
| GET | `/api/usuarios` | ADMIN |
| GET | `/api/usuarios/{id}` | ADMIN |
| POST | `/api/usuarios` | ADMIN |
| PUT | `/api/usuarios/{id}` | ADMIN / próprio usuário |

### Inscrições

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/inscricoes` | Autenticado |
| GET | `/api/inscricoes` | ADMIN |
| GET | `/api/inscricoes/me` | Autenticado |
| DELETE | `/api/inscricoes/{id}` | Autenticado |

### Agendamentos

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/agendamentos` | Autenticado |
| GET | `/api/agendamentos` | ADMIN |
| GET | `/api/agendamentos/{id}` | Autenticado |
| DELETE | `/api/agendamentos/{id}` | Autenticado |

### Visitas

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/visitas` | Autenticado |
| GET | `/api/visitas` | Autenticado |
| GET | `/api/visitas/{id}` | Autenticado |
| DELETE | `/api/visitas/{id}` | Autenticado |

### Sugestões

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/sugestoes` | Autenticado |
| GET | `/api/sugestoes` | Autenticado |
| GET | `/api/sugestoes/{id}` | Autenticado |
| DELETE | `/api/sugestoes/{id}` | Autenticado |

---

## Entregas Acadêmicas

### Entrega 01 e 02 — Planejamento e Design

- **Backlog do Produto:** [Google Docs](https://docs.google.com/document/d/1fHHdMvh4ezyT2cgZMGVdHDAEMCshl091lF6qxCRIG8Y/edit?usp=sharing)
- **Histórias de Usuário (BDD):** [Google Docs](https://docs.google.com/document/d/1rNh6yjRiOeY_gCbTfAXizIdN77B5HpMKccM51h26qmo/edit?usp=sharing)
- **Protótipo Figma:** [Acessar protótipo](https://cold-target-85807250.figma.site/)
- **Screencast:** [Assistir no YouTube](https://youtu.be/EOYhhS9HprU)

### Entrega 03 — MVP

- **Screencast:** [Assistir no YouTube](https://youtu.be/oVw1oaaOWPE)
- **Story Users implementadas:** [Google Docs](https://docs.google.com/document/d/1c6c0XFHAeVdOe4d9dGdpWpzAMPqI-bRmF2OMzn7V9o8/edit?usp=sharing)
- **Issues/Bugtracker:** [Ver issues](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/issues)

### Entrega 04 — Incremento Final

- **Screencast:** [Assistir no YouTube](https://youtu.be/lkn-Brc8j_M)
- **Histórias de Usuário (HU-01 e HU-02):** [historias-usuario.docx](docs/historias-usuario.docx)
- **Issues/Bugtracker:** [Ver issues](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/issues)

<img width="1123" alt="issues" src="https://github.com/user-attachments/assets/74aeece3-3699-425c-a1a0-1f3e1261ed5e" />

<img width="690" height="610" alt="story users" src="https://github.com/user-attachments/assets/9602f689-7ed6-4336-967e-cb4dacf60b89" />

<img alt="issues entrega 04" src="https://raw.githubusercontent.com/brunosm26/Connect_Tickets-Projeto_POO/main/docs/entrega04-issues.png" />

## Histórico

Linha do tempo das entregas, com link direto para cada commit no GitHub.

### Entrega 1 — Setup e estrutura base

**2026-03-16** — Kickoff
- [x] [Initial commit](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/baca0ac) + [README inserido](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/c71a097) / [atualizado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/7c5c086) / [retirado e ajustado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/9565151)
- [x] [Spring Boot application entry point](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/9035021)
- [x] [Configuração H2 + springdoc api-docs](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/efc7b44)
- [x] [Scalar API docs UI em `/docs`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/484c5a4)
- [x] [Maven wrapper + project config](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/c011db6)
- [x] [Test de application context load](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/b53d414)
- [x] [BDDs documentados](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/a4f93c4)
- [x] [Vídeo Story User](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/0b1a60c) e [screencast upado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/94552c3)

**2026-03-17**
- [x] [`.gitignore` para IDE e build artifacts](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/3f1898b)

### Entrega 2 — Estrutura Spring

**2026-03-30 → 2026-04-01**
- [x] [Controllers iniciais](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/0d25b93)
- [x] [Implementação de Service](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/b0879e3)
- [x] [Endpoints de busca por ID + status 201](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/50c1b8f)
- [x] [Fix `setId` + serialização JSON](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/1038283)
- [x] [Repositories revisados](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/46b853e)
- [x] [Estrutura padrão Spring (PR #1)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/16f6f5b)
- [x] [Spring Boot atualizado para 3.4.4 + springdoc-openapi](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/8fc4e27)
- [x] [CONTRIBUTING guide](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/e0ca9f9)
- [x] [`mvnw` com permissão de execução](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/e955287)

### Refator e infra

**2026-04-02**
- [x] [Migração H2 → Supabase PostgreSQL (PR #3)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/21b983c)
- [x] [Docker setup + fix `.gitignore` (PR #4)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/9c9be95)
- [x] [Springdoc OpenAPI habilitado em `/docs` e `/v3/api-docs`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/8a6b70b)
- [x] [Enums: `Role`, `EventCategory`, `SuggestionStatus`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/be6edef)
- [x] [Refator: `isAdmin` boolean → `role` enum](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/487061a)
- [x] [Evento: `image_url`, `location_detail`, `category`, `created_at`, `updated_at`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/41187fc)
- [x] [Sugestão: category + status enums](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/d6f22fa)
- [x] [`CustomUserDetails` usa role enum](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/21282fc)
- [x] [Register sem `isAdmin` + `UsuarioResponseDTO` sem senha](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/6706974)
- [x] [Tabelas de entidades no CONTRIBUTING](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/b730870)

**2026-04-03** — Arquitetura limpa
- [x] [Camada global de tratamento de exceções](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/e693264)
- [x] [Padroniza colunas em inglês + fix JPA](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/68bd5a2)
- [x] [DTOs reestruturados (desacopla API das entidades JPA)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/c27dbd2)
- [x] [JpaRepository tipado corretamente](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/3c47ba8)
- [x] [`SecurityConfig` com permissões corrigidas](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/44b73b6)
- [x] [Lógica de negócio movida para services](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/2ce6c48)
- [x] [Controllers desacoplados das entidades](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/9ee77f0)
- [x] [Testes HTTP gerais da API](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/03a653d)
- [x] [Proposta Lombok nos models](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/f20d7e0)

**2026-04-04**
- [x] [Renomeia `DTOs` → `dtos`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/94f1b35)
- [x] [Fix vulnerabilidades de autorização do code review](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/186aa1a)
- [x] [`AdminController` (PR #6)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/9d18e35)
- [x] [Remove pacote dto duplicado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/cffd21c)
- [x] [Fix prepared statements Supabase/PgBouncer](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/2a2fcd3)
- [x] [CI: GitHub Actions com Checkstyle + SpotBugs](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/cb19946)
- [x] [TO DO atualizado com itens concluídos e pendentes](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/93c7d83) e [CRUDs feitos](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/e603dc5)

### Segurança e refinos

**2026-04-05 → 2026-04-06**
- [x] [Protege `GET /api/usuarios/listar` + esqueleto `Estatistica.java` + validação `VisitaService`](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/3c7ccc6)
- [x] [Substitui `System.out.println` por `log.info` (PR #7)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/ef35e5f)
- [x] [`MockDataInitializer` marcado como feito no TO DO](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/09138e9)

**2026-04-21 → 2026-04-22**
- [x] [Tabelas de status dos módulos no README/CONTRIBUTING](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/db7cbd0)
- [x] [Filtros por categoria em eventos](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/2d1271f)
- [x] [Endpoints de remoção: sugestão, inscrição, visita, agendamento](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/4983316)
- [x] [`SecurityUtils` centraliza autorização (PR #9)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/6d19088)
- [x] [Fix `excluirVisita` duplicado + restaura enums](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/db340ef)
- [x] [Configura porta dinâmica para deploy no Render](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/6b078c1)

### Entrega 3 — MVP

**2026-04-27**
- [x] [README "Entrega 3 - 90%"](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/d337556) → [100%](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/99c0960)
- [x] [Ajustes README finais](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/2d6433c) e [update README](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/dccf767)
- [x] [Link YT atualizado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/7bd8997)
- [x] [README reescrito (técnico + acadêmico unificado)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/66b2f01)
- [x] [CONTRIBUTING simplificado](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/75eafe7)
- [x] [Restaura screenshot do Story Users](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/bbf7dd4)

**2026-04-29**
- [x] [Login retorna dados do usuário e `idUsuario` como claim (PR #16)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/ad217c9)

### Pós-entrega

**2026-05-03 → 2026-05-04**
- [x] [Paginação adicionada](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/c68b10d)
- [x] [`@PageableDefault` + limite máximo de `size` (PR #17)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/fc7f843)
- [x] [`VisitaResponseDTO` com `visitorCount` (PR #18)](https://github.com/brunosm26/Connect_Tickets-Projeto_POO/commit/53915c5)

---

## Equipe

Projeto acadêmico da disciplina de Programação Orientada a Objetos — ADS Regular.
