# Anotação @JsonView #

### A aplicação utiliza a arquitetura Rest, e simula o gerenciamento do cadastro de pessoas. ###

#### ✒️ O Objetivo da aplicação foi estudar a implementação da anotação @JsonView para diminuição do código duplicado. #####

- @JsonView é uma anotação da biblioteca Jackson, utilizada por debaixo dos panos pelo Spring, que ajuda a serializar e deserializar objetos (Java <-> JSON).
- A anotação @JsonView, reduz a base de código, serializa e deserializa apenas os campos com a mesma anotada.

#### 📋 A Utilização da anotação seguem os seguintes passos: ####

1º ✒️ Crie uma classe de view que conterá outras classes/interfaces para gerar as views personalizadas.
``` 
Exemplo:

public class UserViews {

    public static class CompleteData extends RestrictedData {};
    public static class RestrictedData {};
    public static class SaveData {};
}
```

2º ✒️ Adicione na classe DTO a anotação @JsonView, juntamente com as classes de views, encima dos campos que devem visualizados por determinada view.
``` 
Exemplo:

public class UserDto {

    @JsonView(UserViews.RestrictedData.class)
    private Long id;
    
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String name;
    
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String lastname;
    
    @JsonView({UserViews.RestrictedData.class, UserViews.SaveData.class})
    private String email;
    
    @JsonView({UserViews.SaveData.class, UserViews.CompleteData.class})
    private String password;
    
    ## constructors, getters and setters
}
```

3º ✒️ Encima dos endpoints na classe Controller, adicione a anotação @JsonView, juntamente com as classes de views, para gerar a view personalizada.
``` 
Exemplo:

public class UserController {

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
       ## code
    }

    @JsonView(UserViews.RestrictedData.class)
    @GetMapping("/restrict")
    public ResponseEntity<List<UserDto>> findAllRestrictedUsers(String s) {
        ## code
    }
    
    @JsonView(UserViews.SaveData.class)
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto saveData) {
       ## code
    }
}
```

## Tecnologias ## 

- IntelliJ IDEA Community Edition;
- Java v.17;
- Spring Boot v.2.7.2;
- Spring Data JPA;
- @JsonView do Jackson;
- Lombok;
- Model Mapper v.3.1.0;
- JUnit 5;
- Mockito;
- MockMvc;
- Banco de dados H2;
- Postman;

## Estrutura de Dados ##

A aplicação apresenta as seguintes estruturas de dados para as diferentes Views.

**UserViews.RestrictedData.class**
```
{
  "id": "number",
  "name": "string",
  "lastname": "string",
  "email": "string"
}
```
**UserViews.CompleteData.class**
```
{
  "id": "number",
  "name": "string",
  "lastname": "string",
  "email": "string",
  "password": "string"
}
```
**UserViews.SaveData.class**
```
{
  "name": "string",
  "lastname": "string",
  "email": "string",
  "password": "string"
}
```
**UserDTO.class**
```
{
  "id": "number",
  "name": "string",
  "lastname": "string",
  "email": "string",
  "password": "string"
}
```

### 📋 Principais Desafios ###

O principal desafio foi:
  - ✒️ Testes Unitários - Entender como deveria ser montado o cenário para os testes dos endpoints.
