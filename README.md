
# 🧍‍♂️ API de Gerenciamento de Pessoas

Uma aplicação **Spring Boot** simples que implementa um **CRUD (Create, Read, Update, Delete)** para gerenciar registros de pessoas.  
O projeto demonstra boas práticas com **DTOs, validação com Bean Validation, camada de serviço, repositório JPA** e **tratamento básico de erros**.

---

## 📚 Índice

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Entidades e DTOs](#-entidades-e-dtos)
- [Camadas do Sistema](#-camadas-do-sistema)
  - [Model](#model)
  - [DTO](#dto)
  - [Repository](#repository)
  - [Service](#service)
  - [Controller](#controller)
- [Rotas da API](#-rotas-da-api)
- [Validações](#-validações)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Possíveis Melhorias Futuras](#-possíveis-melhorias-futuras)

---

## ⚙️ Tecnologias Utilizadas

- **Java 21+**  
- **Spring Boot 3+**  
- **Spring Web**  
- **Spring Data JPA**  
- **Hibernate**  
- **Bean Validation (Jakarta Validation)**  
- **Banco de Dados MySQL**  

---

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)** com uma camada intermediária de **Service** para a lógica de negócios:

```
Controller → Service → Repository → Banco de Dados
```

Cada camada tem responsabilidade clara e separada, facilitando manutenção, testes e evolução do sistema.

---

## 🧩 Entidades e DTOs

### PessoaModel (Entidade)

Representa a tabela `pessoas` no banco de dados.

| Campo | Tipo | Restrições |
|--------|------|------------|
| id | Long | Gerado automaticamente |
| nome | String | Não pode ser nulo |
| cpf | String | Não pode ser nulo |
| idade | int | Opcional |

```java
@Entity
@Table(name= "pessoas")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class PessoaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String nome;

  @Column(nullable=false)
  private String cpf;

  private int idade;
}
```

---

### PessoaDTO (Data Transfer Object)

Usado para trafegar dados entre as camadas sem expor a entidade diretamente.  
Inclui **validações** usando anotações da Bean Validation.

```java
public record PessoaDTO(
  Long id,
  
  @NotBlank(message="O nome não pode ser vazio")
  String nome,

  @NotBlank(message="O cpf não pode ser vazio")
  String cpf,

  @Min(value=1, message="Idade inválida")
  @Max(value=105, message="Idade inválida")
  int idade
) {}
```

---

## 🗃️ Repository

Interface responsável pela comunicação com o banco de dados.  
Extende `JpaRepository`, fornecendo métodos prontos como `save()`, `findAll()`, `findById()` e `deleteById()`.

```java
@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long>{}
```

---

## 🧠 Service

Camada que contém a **lógica de negócios** da aplicação.  
Faz conversão entre `PessoaModel` e `PessoaDTO`, e trata exceções básicas.

Principais métodos:
- `listarTodos()` — retorna todas as pessoas em formato DTO  
- `salvarDto()` — valida e salva uma nova pessoa  
- `findById()` — busca uma pessoa pelo ID  
- `delete()` — remove uma pessoa  
- `atualizar()` — atualiza dados de uma pessoa existente  

```java
@Service
public class PessoaService {
  @Autowired
  private PessoaRepository pessoaRepository;

  public List<PessoaDTO> listarTodos() { ... }
  public PessoaDTO salvarDto(PessoaDTO dto) { ... }
  public PessoaDTO findById(Long id) { ... }
  public void delete(Long id) { ... }
  public PessoaDTO atualizar(Long id, PessoaDTO dto) { ... }
}
```

---

## 🌐 Controller

Define os **endpoints REST** da API.

```java
@RestController
@RequestMapping("/pessoas")
public class PessoaController {
  @Autowired
  private PessoaService pessoaService;

  @PostMapping
  public ResponseEntity<PessoaDTO> salvar(@Valid @RequestBody PessoaDTO dto) { ... }

  @GetMapping
  public ResponseEntity<List<PessoaDTO>> getAll() { ... }

  @GetMapping("{id}")
  public ResponseEntity<PessoaDTO> bucarPorId(@PathVariable Long id) { ... }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) { ... }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaDTO dto) { ... }
}
```

---

## 🔗 Rotas da API

| Método | Endpoint | Descrição | Corpo da Requisição | Exemplo de Resposta |
|:--|:--|:--|:--|:--|
| **POST** | `/pessoas` | Cadastrar nova pessoa | `{ "nome": "João", "cpf": "12345678900", "idade": 25 }` | `{ "id": 1, "nome": "João", "cpf": "12345678900", "idade": 25 }` |
| **GET** | `/pessoas` | Listar todas as pessoas | — | `[ {...}, {...} ]` |
| **GET** | `/pessoas/{id}` | Buscar pessoa por ID | — | `{ "id": 1, "nome": "João", "cpf": "12345678900", "idade": 25 }` |
| **PUT** | `/pessoas/{id}` | Atualizar dados de uma pessoa | `{ "nome": "Maria", "cpf": "98765432100", "idade": 30 }` | `{ "id": 1, "nome": "Maria", "cpf": "98765432100", "idade": 30 }` |
| **DELETE** | `/pessoas/{id}` | Excluir uma pessoa | — | `204 No Content` |

---

## ✅ Validações

- `@NotBlank`: impede campos de texto vazios.  
- `@Min` / `@Max`: limita valores numéricos (ex: idade entre 1 e 105).  
- Tratamentos complementares de validação também estão presentes na camada de **service**.

---

## 🚀 Como Executar o Projeto

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/seu-usuario/pessoa-api.git
   cd pessoa-api
   ```

2. **Executar com Maven**
   ```bash
   mvn spring-boot:run
   ```

3. **Acessar no navegador ou via Postman**
   ```
   http://localhost:8080/pessoas
   ```

---

## 💡 Etapa de estudo: 1/12
  Este projeto tem como objetivo a repetição e o treino do básico, para fixar o conhecimento necessário para um programador backend
  Etapas:
  - CRUD
  - ErrorHandling
  - Validação
  - Segurança (JWT)
  - Bando de dados avançado
  - Logs de Monitoramento
  - Teste Unitários
  - Swagger
  - Configuração de Perfil
  - Docker + Deploy
  - Integrações externas
  - Boas práticas da arquitetura
  

---
